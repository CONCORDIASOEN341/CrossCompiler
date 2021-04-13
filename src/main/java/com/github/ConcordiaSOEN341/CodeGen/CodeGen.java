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

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CodeGen implements ICodeGen {
    private final SymbolTable symbolTable;
    private final HashMap<Integer, IOpCodeTableElement> opCodeTable = new HashMap<>();
    private final ArrayList<ILineStatement> iR;
    private final IErrorReporter reporter;

    public CodeGen(SymbolTable sT, ArrayList<ILineStatement> ir, IErrorReporter e){
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

                String[] listings = listing(iR);

                for (String listing : listings) {
                    listingWriter.write(listing);
                }

                listingWriter.close();
            } catch (IOException e) {
                System.out.println("An error occurred");
                System.out.println("The program will terminate.");
                e.printStackTrace();
                System.exit(0);
            }
        }
    }

    public String[] listing(ArrayList<ILineStatement> ir) {
        SymbolTable codeGen = new SymbolTable();
        String[] listings = new String[ir.size()];
        String hexAddress = "0000";
        String offset = "";
        String comment = "";
        int skips = 0;

        for (int i = 0; i < ir.size(); i++) {

            if (StringUtils.isEmpty(ir.get(i).getInstruction().toString()) && i > 2) {
                skips++;
                hexAddress = String.format("%04X", i + 1 - skips);
            } else if (StringUtils.isEmpty(ir.get(i).getInstruction().toString())) {
                skips++;
            } else {
                hexAddress = String.format("%04X", i - skips);
            }

            String codeMnemonic = "";

            String mnemonic = ir.get(i).getInstruction().toString();
            offset = ir.get(i).getInstruction().getOperand().getTokenString();
            comment = ir.get(i).getComment().getTokenString();

            if (StringUtils.isNotEmpty(ir.get(i).getInstruction().getOperand().getTokenString())) {
                codeMnemonic = codeGen.determineOpCode(mnemonic, offset);
            }


            listings[i] = ((i + 1) + "\t " + hexAddress + " " + codeMnemonic + " \t\t\t  \t\t\t  " + mnemonic + " \t " + offset + "\t\t\t\t" + comment + " \t\n");
        }
        return listings;
    }

    @Override
    public HashMap<Integer, IOpCodeTableElement> generateOpCodeTable() {
        int line = 1;
        int address = 0;

        // FIRST PASS
        for(ILineStatement lS : iR){
            IOpCodeTableElement oTE = new OpCodeTableElement();

            // Set Line and Address
            oTE.setLine(line);
            oTE.setAddress(String.format("%04X", address));

            // Add Label - Address to symbol table
            if(lS.getLabel() != null){
                symbolTable.addEntry(lS.getLabel().getTokenString(), String.format("%04X", address));
            }

            // Account for Instruction or Directive
            if(lS.getInstruction() != null){
                // Determine opcode of mnemonic if there is an instruction
                if(lS.getInstruction().getInstructionType() == InstructionType.IMMEDIATE){
                    oTE.setOpCode(calculateImmediateOpCode(lS.getInstruction()));
                } else {
                    oTE.setOpCode(symbolTable.getValue(lS.getInstruction().getMnemonic().getTokenString()));
                }

                // Inc address for the opcode
                address++;

                // Determine Hex for operand (label or integer)
                if(lS.getInstruction().getInstructionType() == InstructionType.RELATIVE) {
                    oTE.setBitSpace(bitSpace(lS.getInstruction())/4); // Based on mnemonic
                    try{
                        // FIGURE OUT NEGATIVES
                        int operand = Integer.parseInt(lS.getInstruction().getOperand().getTokenString());
                        oTE.addOperand(String.format("%0"+oTE.getBitSpace()+"X", operand));

                    } catch (NumberFormatException e){
                        oTE.setLabel(lS.getInstruction().getOperand().getTokenString());
                    }
                    // Inc address for relative operand
                    address += oTE.getBitSpace()/2;
                }

            } else if(lS.getDirective() != null && lS.getDirective().getDirectiveName().equals(".cstring")){
                String cstring = lS.getDirective().getCString();

                for (char c : cstring.toCharArray()){
                    if(c != '\"'){
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
        for(IOpCodeTableElement oTE : opCodeTable.values()){
            if(oTE.getLabel() != null){
                String labelAddress = symbolTable.getValue(oTE.getLabel());
                if(labelAddress != null){
                    int offset = Integer.parseInt(labelAddress,16) - Integer.parseInt(oTE.getAddress(),16);
                    if(offset < 0){
                        String offsetString = String.format("%X", offset);
                        oTE.addOperand(offsetString.substring(offsetString.length()-oTE.getBitSpace()));
                    } else {
                        oTE.addOperand(String.format("%0"+oTE.getBitSpace()+"X", offset));
                    }

                }
            }
        }

        return opCodeTable;
    }

    private int bitSpace(IInstruction instr){
        String[] sNum = instr.getMnemonic().getTokenString().split(".((u)|(i))", 2);            //take string after the u or the i (this leaves only the number)
        return Integer.parseInt(sNum[1]);
    }

    private String calculateImmediateOpCode(IInstruction instr){
        String mnemonic = instr.getMnemonic().getTokenString();
        int offset = Integer.parseInt(instr.getOperand().getTokenString());
        int hexNumber = 0;

        //special case for enter.u5, offset do not increase normally
        if (mnemonic.equals("enter.u5")){
            if (offset <= 15) {
                hexNumber = Integer.parseInt("80", 16) + offset;
            } else {
                hexNumber = Integer.parseInt("60", 16) + offset;
            }
        }
        else {
            //special case for negative numbers
            if (offset < 0 ) {
                int size = (int)Math.pow(2,bitSpace(instr));
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
