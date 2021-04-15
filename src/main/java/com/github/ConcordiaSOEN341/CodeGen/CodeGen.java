package com.github.ConcordiaSOEN341.CodeGen;

import com.github.ConcordiaSOEN341.Interfaces.*;
import com.github.ConcordiaSOEN341.Lexer.TokenType;
import com.github.ConcordiaSOEN341.Logger.LoggerFactory;
import com.github.ConcordiaSOEN341.Logger.LoggerType;
import com.github.ConcordiaSOEN341.Parser.InstructionType;
import com.github.ConcordiaSOEN341.Tables.OpCodeTableElement;
import com.github.ConcordiaSOEN341.Tables.SymbolTable;

import java.io.*;
import java.util.ArrayList;

public class CodeGen implements ICodeGen {
    private ArrayList<ILineStatement> iR = new ArrayList<>();
    private final ArrayList<IOpCodeTableElement> opCodeTable = new ArrayList<>();
    private final SymbolTable symbolTable;
    private final IErrorReporter reporter;
    private final ILogger logger;

    public CodeGen(ArrayList<ILineStatement> ir, SymbolTable sT, LoggerFactory lf, IErrorReporter e) {
        iR = ir;
        symbolTable = sT;
        logger = lf.getLogger(LoggerType.CODEGEN);
        reporter = e;
    }

    public CodeGen(SymbolTable sT, LoggerFactory lf, IErrorReporter e) {
        symbolTable = sT;
        logger = lf.getLogger(LoggerType.CODEGEN);
        reporter = e;
    }

    @Override
    public void setIR(ArrayList<ILineStatement> ir) {
        iR = ir;
    }

    @Override
    public void generateListingFile(String fileName) {
        String listFile = fileName.substring(0, fileName.length() - 4) + ".lst";
        logger.log("creating listing file \"" + listFile + "\"");
        try {
            FileWriter listingWriter = new FileWriter(listFile);
            String[] byteCode = listingOP();
            String[] label = listingIRLabel();
            String[] mne = listingIRMne();
            String[] operands = listingIROps();
            String[] comments = listingIRComments();

            for (int i = 0; i < iR.size(); i++) {
                listingWriter.write(byteCode[i]);
                listingWriter.write(label[i]);
                listingWriter.write(mne[i]);
                listingWriter.write(operands[i]);
                listingWriter.write(comments[i]);
            }
            

            listingWriter.close();
        } catch (IOException e) {
            //TODO: USE ERROR REPORTER HERE
            // THEN LOG ERROR IN ERROR REPORTER
            System.out.println("An error occurred");
            System.out.println("The program will terminate.");
            e.printStackTrace();
            System.exit(0);
        }
    }

    @Override
    public String[] listingOP() {
        String[] arr = new String[iR.size()];
        StringBuilder[] sbLines = new StringBuilder[iR.size()];

        // Init Header
        sbLines[0] = new StringBuilder();
        sbLines[0].append("Line Addr Code");
        int realHeaderLength = sbLines[0].length() - 2;
        int maxLineLength = realHeaderLength;

        for (int i = 0; i < iR.size() - 1; i++) {
            StringBuilder sb = new StringBuilder();

            sb.append(i + 1).append("\t ").append(opCodeTable.get(i).getAddress()).append(" ");

            if (opCodeTable.get(i).getOpCode().length() > 0) {
                sb.append(opCodeTable.get(i).getOpCode()).append(" ");
            }

            for (String op : opCodeTable.get(i).getOperands()) {
                sb.append(op).append(" ");
            }

            maxLineLength = Math.max(maxLineLength, sb.length());
            sbLines[i + 1] = sb;
        }

        sbLines[0].append(" ".repeat(maxLineLength - realHeaderLength)).append("\t");
        arr[0] = sbLines[0].toString();

        for (int i = 1; i < iR.size(); i++) {
            sbLines[i].append(" ".repeat(maxLineLength - sbLines[i].length())).append("\t");
            arr[i] = sbLines[i].toString();
        }

        return arr;
    }

    @Override
    public String[] listingIRLabel() {
        String[] arr = new String[iR.size()];
        StringBuilder[] sbLines = new StringBuilder[iR.size()];

        // Init Header
        sbLines[0] = new StringBuilder();
        sbLines[0].append("Label");
        int maxLabelLength = sbLines[0].length();

        for (int i = 0; i < iR.size() - 1; i++) {
            StringBuilder sb = new StringBuilder();

            sb.append(iR.get(i).getLabel().getTokenString());

            maxLabelLength = Math.max(maxLabelLength, sb.length());
            sbLines[i + 1] = sb;
        }

        for (int i = 0; i < iR.size(); i++) {
            sbLines[i].append(" ".repeat(maxLabelLength - sbLines[i].length())).append("\t\t");
            arr[i] = sbLines[i].toString();
        }

        return arr;

    }

    @Override
    public String[] listingIRMne() {
        String[] arr = new String[iR.size()];
        StringBuilder[] sbLines = new StringBuilder[iR.size()];

        // Init Header
        sbLines[0] = new StringBuilder();
        sbLines[0].append("Mne");
        int maxCoreLength = sbLines[0].length();

        for (int i = 0; i < iR.size() - 1; i++) {
            StringBuilder sb = new StringBuilder();

            if (iR.get(i).getInstruction().getMnemonic().getTokenString().equals("")) {
                sb.append(iR.get(i).getDirective().getDir().getTokenString());
            } else {
                sb.append(iR.get(i).getInstruction().getMnemonic().getTokenString());
            }

            maxCoreLength = Math.max(maxCoreLength, sb.length());
            sbLines[i + 1] = sb;
        }

        for (int i = 0; i < iR.size(); i++) {
            sbLines[i].append(" ".repeat(maxCoreLength - sbLines[i].length())).append("  ");
            arr[i] = sbLines[i].toString();
        }

        return arr;
    }

    @Override
    public String[] listingIROps() {
        String[] arr = new String[iR.size()];
        StringBuilder[] sbLines = new StringBuilder[iR.size()];

        // Init Header
        sbLines[0] = new StringBuilder();
        sbLines[0].append("Operand");
        int maxCoreLength = sbLines[0].length();

        for (int i = 0; i < iR.size() - 1; i++) {
            StringBuilder sb = new StringBuilder();

            if (iR.get(i).getInstruction().getMnemonic().getTokenString().equals("")) {
                sb.append(iR.get(i).getDirective().getCString().getTokenString());
            } else {
                sb.append(iR.get(i).getInstruction().getOperand().getTokenString());
            }

            maxCoreLength = Math.max(maxCoreLength, sb.length());
            sbLines[i + 1] = sb;
        }

        for (int i = 0; i < iR.size(); i++) {
            sbLines[i].append(" ".repeat(maxCoreLength - sbLines[i].length())).append("\t\t");
            arr[i] = sbLines[i].toString();
        }

        return arr;
    }

    @Override
    public String[] listingIRComments() {
        String[] arr = new String[iR.size()];
        StringBuilder[] sbLines = new StringBuilder[iR.size()];

        // Init Header
        sbLines[0] = new StringBuilder();
        sbLines[0].append("Comments");
        int maxCommentLength = sbLines[0].length();

        for (int i = 0; i < iR.size() - 1; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(iR.get(i).getComment().getTokenString());

            maxCommentLength = Math.max(maxCommentLength, sb.length());
            sbLines[i + 1] = sb;
        }

        for (int i = 0; i < iR.size(); i++) {
            sbLines[i].append(" ".repeat(maxCommentLength - sbLines[i].length())).append("\t\n");
            arr[i] = sbLines[i].toString();
        }

        return arr;
    }

    @Override
    public void generateExe(String fileName) {
        String listFile = fileName.substring(0, fileName.length() - 4) + ".exe";
        logger.log("Generating Executable file \"" + listFile + "\"");
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

    @Override
    public String generateByteCode() {
        //String
        logger.log("Generating byte code...");
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
        logger.log("Byte code generated " + sb);
        return sb.toString();
    }

    @Override
    public ArrayList<IOpCodeTableElement> generateOpCodeTable() {
        int line = 1;
        int address = 0;
        logger.log("Generating OpCodeTable");
        logger.log("Starting First Pass now...");
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
        logger.log("Completed First Pass");
        logger.log("Starting Second Pass now...");
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
        logger.log("Completed Second Pass...");
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
}
