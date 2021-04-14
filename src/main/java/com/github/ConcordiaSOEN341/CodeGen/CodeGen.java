package com.github.ConcordiaSOEN341.CodeGen;

import com.github.ConcordiaSOEN341.Interfaces.*;
import com.github.ConcordiaSOEN341.Lexer.Position;
import com.github.ConcordiaSOEN341.Lexer.Token;
import com.github.ConcordiaSOEN341.Lexer.TokenType;
import com.github.ConcordiaSOEN341.Parser.Instruction;
import com.github.ConcordiaSOEN341.Parser.InstructionType;
import com.github.ConcordiaSOEN341.Tables.OpCodeTableElement;
import com.github.ConcordiaSOEN341.Tables.SymbolTable;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class CodeGen implements ICodeGen {
    private final SymbolTable symbolTable;
    private final HashMap<Integer, IOpCodeTableElement> opCodeTable = new HashMap<>();
    private final ArrayList<ILineStatement> iR;
    private final IErrorReporter reporter;

    public CodeGen(SymbolTable sT, ArrayList<ILineStatement> ir, IErrorReporter e) {
        symbolTable = sT;
        iR = ir;
        reporter = e;
    }

    public void generateListingFile(String fileName) {
        if (reporter.hasErrors()) {
            System.out.println(reporter.report(fileName));
            System.exit(0);
        } else {
            String listFile = fileName.substring(0, fileName.length() - 4) + ".lst";
            try {
                FileWriter listingWriter = new FileWriter(listFile);
                listingWriter.write("Line Addr Code \t\t\tLabel \t\t  Mne \t\tOperand \t\tComments\n");

                String[] listings = listing();

                for (String listing : listings) {
                    listingWriter.write(listing);
                }

                System.out.println(generateByteCode());

                listingWriter.close();
            } catch (IOException e) {
                System.out.println("An error occurred");
                System.out.println("The program will terminate.");
                e.printStackTrace();
                System.exit(0);
            }
        }
    }

    public String[] listing() {
        String[] listings = new String[iR.size() - 1];
        for (int i = 0; i < iR.size() - 1; i++) {

            listings[i] = ((i + 1) + "\t " + opCodeTable.get(i + 1).getAddress() + " " + opCodeTable.get(i + 1).getOpCode() + " \t\t\t  \t\t\t  " + iR.get(i).getInstruction().getMnemonic().getTokenString() + " \t " + iR.get(i).getInstruction().getOperand().getTokenString() + "\t\t\t\t" + iR.get(i).getComment().getTokenString() + " \t\n");
        }
        return listings;
    }

    public void generateExe(String fileName) {
        if (reporter.hasErrors()) {
            System.out.println(reporter.report(fileName));
            System.exit(0);
        } else {
            String listFile = fileName.substring(0, fileName.length() - 4) + ".exe";
            try {
                //FileWriter listingWriter = new FileWriter(listFile);
                FileOutputStream fStream = new FileOutputStream(listFile);
                DataOutputStream data = new DataOutputStream(fStream);

                data.writeBytes(generateByteCode());


                fStream.close();
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred");
                System.out.println("The program will terminate.");
                e.printStackTrace();
                System.exit(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public String generateByteCode() {
        //String
        StringBuilder sb = new StringBuilder();
        for (IOpCodeTableElement oTE : opCodeTable.values()) {
            if (oTE.getOpCode().length() > 0) {
                sb.append(oTE.getOpCode());
                sb.append(" ");
            }
            for (String oP : oTE.getOperands()) {
                if (oP.length() > 0) {
                    if (oP.length() == 4) {
                        sb.append(oP.substring(0, 2));
                        sb.append(" ");
                        sb.append(oP.substring(2, 4));
                        sb.append(" ");
                    } else {
                        sb.append(oP);
                        sb.append(" ");
                    }
                }
            }
        }

        return sb.toString();
    }

    @Override
    public HashMap<Integer, IOpCodeTableElement> generateOpCodeTable() {
        int line = 1;
        int address = 0;

        // FIRST PASS
        for (ILineStatement lS : iR) {
            IOpCodeTableElement oTE = new OpCodeTableElement();

            // Set Line and Address
            oTE.setLine(line);
            oTE.setAddress(String.format("%04X", address));

            // Add Label - Address to symbol table
            if (lS.getLabel().getTokenType() != TokenType.ERROR) {
                symbolTable.addEntry(lS.getLabel().getTokenString(), String.format("%04X", address));
            }

            // Account for Instruction or Directive
            if (lS.getInstruction().getInstructionType() != null) {
                // Determine opcode of mnemonic if there is an instruction
                if (lS.getInstruction().getInstructionType() == InstructionType.IMMEDIATE) {
                    oTE.setOpCode(calculateImmediateOpCode(lS.getInstruction()));
                } else {
                    oTE.setOpCode(symbolTable.getValue(lS.getInstruction().getMnemonic().getTokenString()));
                }

                // Inc address for the opcode
                address++;

                // Determine Hex for operand (label or integer)
                if (lS.getInstruction().getInstructionType() == InstructionType.RELATIVE) {
                    oTE.setBitSpace(bitSpace(lS.getInstruction()) / 4); // Based on mnemonic
                    try {
                        // FIGURE OUT NEGATIVES
                        int operand = Integer.parseInt(lS.getInstruction().getOperand().getTokenString());
                        oTE.addOperand(String.format("%0" + oTE.getBitSpace() + "X", operand));

                    } catch (NumberFormatException e) {
                        oTE.setLabel(lS.getInstruction().getOperand().getTokenString());
                    }
                    // Inc address for relative operand
                    address += oTE.getBitSpace() / 2;
                }

            } else if (lS.getDirective().getDir().getTokenType() != TokenType.ERROR && lS.getDirective().getDir().getTokenString().equals(".cstring")) {
                String cstring = lS.getDirective().getCString().getTokenString();

                for (char c : cstring.toCharArray()) {
                    if (c != '\"') {
                        oTE.addOperand(String.format("%02X", (int) c));
                        address++;
                    }
                }
                oTE.addOperand("00");
                address++;
            }

            opCodeTable.put(line, oTE);
            line++;
        }

        // SECOND PASS
        for (IOpCodeTableElement oTE : opCodeTable.values()) {
            if (oTE.getLabel() != null) {
                String labelAddress = symbolTable.getValue(oTE.getLabel());
                if (labelAddress != null) {
                    int offset = Integer.parseInt(labelAddress, 16) - Integer.parseInt(oTE.getAddress(), 16);
                    if (offset < 0) {
                        String offsetString = String.format("%X", offset);
                        oTE.addOperand(offsetString.substring(offsetString.length() - oTE.getBitSpace()));
                    } else {
                        oTE.addOperand(String.format("%0" + oTE.getBitSpace() + "X", offset));
                    }
                }
            }
        }

        return opCodeTable;
    }

    private int bitSpace(IInstruction instr) {
        String[] sNum = instr.getMnemonic().getTokenString().split(".((u)|(i))", 2);            //take string after the u or the i (this leaves only the number)
        return Integer.parseInt(sNum[1]);
    }

    private String calculateImmediateOpCode(IInstruction instr) {
        String mnemonic = instr.getMnemonic().getTokenString();
        int offset = Integer.parseInt(instr.getOperand().getTokenString());
        int hexNumber = 0;

        //special case for enter.u5, offset do not increase normally
        if (mnemonic.equals("enter.u5")) {
            if (offset <= 15) {
                hexNumber = Integer.parseInt("80", 16) + offset;
            } else {
                hexNumber = Integer.parseInt("60", 16) + offset;
            }
        } else {
            //special case for negative numbers
            if (offset < 0) {
                int size = (int) Math.pow(2, bitSpace(instr));
                offset = size + offset;
            }
            //the rest
            hexNumber = Integer.parseInt(symbolTable.getValue(mnemonic), 16) + offset;
        }


        return String.format("%02X", hexNumber);
    }
    //For testing purposes
//    public static void main(String[] args) {
//        SymbolTable s = new SymbolTable();
//        CodeGen c = new CodeGen(s);
//        Instruction i = new Instruction();
//        i.setMnemonic(new Token( "br.i5", new Position(1,1,1), TokenType.MNEMONIC));
//        i.setOffset(new Token( "-2", new Position(1,1,1), TokenType.OFFSET));
//        System.out.print(c.calculateImmediateOpCode(i));
    //System.out.print(c.bitSpace(i));
//    }
}
