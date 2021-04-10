package com.github.ConcordiaSOEN341.CodeGen;

import com.github.ConcordiaSOEN341.Interfaces.*;
import com.github.ConcordiaSOEN341.Parser.InstructionType;
import com.github.ConcordiaSOEN341.Tables.OpCodeTableElement;
import com.github.ConcordiaSOEN341.Tables.SymbolTable;
import org.apache.commons.lang3.StringUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CodeGen implements ICodeGen {
    private SymbolTable symbolTable;
    private final HashMap<Integer, OpCodeTableElement> opCodeTable = new HashMap<>();

    public CodeGen(SymbolTable sT){
        symbolTable = sT;
    }

    public void generateListingFile(String fileName, ArrayList<ILineStatement> ir, IErrorReporter reporter) {
        if (reporter.hasErrors()) {
            System.out.println(reporter.report(fileName));
            System.exit(0);
        } else {
            String listFile = fileName.substring(0, fileName.length() - 4) + ".lst";
            try {
                FileWriter listingWriter = new FileWriter(listFile);
                listingWriter.write("Line Addr Code \t\t\tLabel \t\t  Mne \t\tOperand \t\tComments\n");

                String[] listings = listing(ir);

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
            offset = ir.get(i).getInstruction().getOffset().getTokenString();
            comment = ir.get(i).getComment().getTokenString();

            if (StringUtils.isNotEmpty(ir.get(i).getInstruction().getOffset().getTokenString())) {
                codeMnemonic = codeGen.determineOpCode(mnemonic, offset);
            }


            listings[i] = ((i + 1) + "\t " + hexAddress + " " + codeMnemonic + " \t\t\t  \t\t\t  " + mnemonic + " \t " + offset + "\t\t\t\t" + comment + " \t\n");
        }
        return listings;
    }

    @Override
    public void generateOpCodeTable(ArrayList<ILineStatement> ir) {
        int line = 1;
        int address = 0;

        for(ILineStatement lS : ir){
            OpCodeTableElement oTE = new OpCodeTableElement();

            // Set Line and Address
            oTE.setLine(line);
            oTE.setAddress(String.format("%04X", address));

            // Add Label - Address to symbol table
            if(lS.getLabel() != null){
                symbolTable.addEntry(lS.getLabel().getTokenString(), String.format("%04X", address));
            }


            if(lS.getInstruction() != null){
                // Determine opcode of mnemonic if there is an instruction
                if(lS.getInstruction().getInstructionType() == InstructionType.IMMEDIATE){
                    oTE.setOpCode(calculateImmediateOpCode(lS.getInstruction()));
                } else {
                    oTE.setOpCode(symbolTable.getValue(lS.getInstruction().getMnemonic().getTokenString()));
                }

                // Determine Hex for operand (label or integer)
                if(lS.getInstruction().getInstructionType() == InstructionType.RELATIVE) {
                    try{
                        int operand = Integer.parseInt(lS.getInstruction().getOffset().getTokenString());
                        oTE.addOperand(String.format("%X", operand));

                    } catch (NumberFormatException e){
                        oTE.setLabel(lS.getInstruction().getOffset().getTokenString());
                    }
                }

            } else if(lS.getDirective() != null && lS.getDirective().getDirectiveName().equals(".cstring")){
                String cstring = lS.getDirective().getCString();

                for (char c : cstring.toCharArray()){
                    if(c != '\"'){
                        oTE.addOperand(String.format("%02X", (int) c));
                    }
                }
                oTE.addOperand("00");
            }


            line++;
        }

    }

    private int bitSpace(IInstruction instr){
        String[] sNum = instr.getMnemonic().getTokenString().split(".((u)|(i))", 2);            //take string after the u or the i (this leaves only the number)
        return Integer.parseInt(sNum[1]);
    }

    private String calculateImmediateOpCode(IInstruction instr){
        // FIGURE OUT HOW TO CALCULATE OpCode for IMMEDIATE INSTRUCTIONS (Account for integers and labels)
        // SEE: determineOpCode() in symbol table (Vincent code)
        // SEE: Michel Email (SOEN-341-2204-S: How to generate the opcode byte for an immediate instruction)

        Integer.toHexString(
                Integer.parseInt(symbolTable.getValue(instr.getMnemonic().getTokenString())) +
                        Integer.parseInt(instr.getOffset().getTokenString()));
    }


}
