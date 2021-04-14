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
    private ArrayList<ILineStatement> iR = new ArrayList<>();
    private final ArrayList<IOpCodeTableElement> opCodeTable = new ArrayList<>();
    private final SymbolTable symbolTable;
    private final IErrorReporter reporter;

    public CodeGen(ArrayList<ILineStatement> ir, SymbolTable sT, IErrorReporter e) {
        iR = ir;
        symbolTable = sT;
        reporter = e;
    }

    public CodeGen(SymbolTable sT, IErrorReporter e) {
        symbolTable = sT;
        reporter = e;
    }

    public void setIR(ArrayList<ILineStatement> ir) {
        iR = ir;
    }

    public void generateListingFile(String fileName) {
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

    public String[] listing() {
        int maxOpSpace = 0;
        int maxOperandSpace = 0;
        String[] listings = new String[iR.size() - 1];

        for(int i = 0; i < iR.size() - 1; i++){
            maxOpSpace = Math.max(opCodeTable.get(i).getOperands().size(),maxOpSpace);
            maxOperandSpace = Math.max(iR.get(i).getInstruction().getOperand().getTokenString().length(),maxOperandSpace);
            maxOperandSpace = Math.max(iR.get(i).getDirective().getCString().getTokenString().length(),maxOperandSpace);
        }

        for (int i = 0; i < iR.size() - 1; i++) {
            StringBuilder lstSB = new StringBuilder();
            StringBuilder opSB = new StringBuilder();
            String addTabs;
            int numAddTabs = 0;

            // Start Building listing string
            lstSB.append(i + 1).append("\t ").append(opCodeTable.get(i).getAddress()).append(" ");

            if(opCodeTable.get(i).getOpCode().length() > 0){
                lstSB.append(opCodeTable.get(i).getOpCode()).append(" ");
            } else {
                numAddTabs += 2;
            }

            // Determine Tabs after hex operands
            int opElements = opCodeTable.get(i).getOperands().size();

            for (String op : opCodeTable.get(i).getOperands()) {
                opSB.append(op).append(" ");
            }

            numAddTabs += maxOpSpace - opElements - ((opCodeTable.get(i).getOperands().isEmpty())? 0 : 1);

            addTabs = "\t".repeat(numAddTabs);

            lstSB.append(opSB).append(addTabs);

            // /\/\/\ OP CODE TABLE DATA
            // ---------------------
            // \/\/\/ IR DATA

            opElements = Math.max(iR.get(i).getInstruction().getOperand().getTokenString().length(),iR.get(i).getDirective().getCString().getTokenString().length()) ;
            maxOperandSpace = maxOperandSpace - maxOperandSpace%4;
            opElements = opElements - opElements%4;

            numAddTabs = (maxOperandSpace - opElements)/4 + 2;

            addTabs = "\t".repeat(numAddTabs);


            if(iR.get(i).getLabel().getTokenString().length() > 0){
                lstSB.append(iR.get(i).getLabel().getTokenString());
            } else {
                lstSB.append("\t");
            }

            lstSB.append("\t\t  ");

            if (iR.get(i).getInstruction().getMnemonic().getTokenString().equals("")) {
                lstSB.append(iR.get(i).getDirective().getDir().getTokenString()).append("\t").append(iR.get(i).getDirective().getCString().getTokenString());
            } else {
                lstSB.append(iR.get(i).getInstruction().getMnemonic().getTokenString()).append("\t").append(iR.get(i).getInstruction().getOperand().getTokenString());
            }

            lstSB.append(addTabs).append(iR.get(i).getComment().getTokenString()).append("\t\n");

            listings[i] = lstSB.toString();
        }
        return listings;
    }

    public void generateExe(String fileName) {
        String listFile = fileName.substring(0, fileName.length() - 4) + ".exe";
        try {
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


    public String generateByteCode() {
        //String
        StringBuilder sb = new StringBuilder();
        for (IOpCodeTableElement oTE : opCodeTable) {
            if (oTE.getOpCode().length() > 0) {
                sb.append(oTE.getOpCode());
                sb.append(" ");
            }
            for (String oP : oTE.getOperands()) {
                sb.append(oP, 0, 2);
                sb.append(" ");
                if (oP.length() == 4) {
                    sb.append(oP, 2, 4);
                    sb.append(" ");
                }
            }
        }

        return sb.toString();
    }

    @Override
    public ArrayList<IOpCodeTableElement> generateOpCodeTable() {
        int line = 1;
        int address = 0;

        // FIRST PASS
        for (ILineStatement lS : iR) {
            IOpCodeTableElement oTE = new OpCodeTableElement();

            // Set Line and Address
            oTE.setLine(line);
            oTE.setAddress(String.format("%04X", address));

            // Add Label - Address to symbol table
            if (lS.getLabel().getTokenType() != TokenType.ERROR || lS.getLabel() != null) {
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

            opCodeTable.add(oTE);
            line++;
        }

        // SECOND PASS
        for (IOpCodeTableElement oTE : opCodeTable) {
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
