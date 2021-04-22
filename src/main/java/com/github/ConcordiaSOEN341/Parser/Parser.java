package com.github.ConcordiaSOEN341.Parser;

import com.github.ConcordiaSOEN341.CodeGen.IOpCodeTableElement;
import com.github.ConcordiaSOEN341.CodeGen.OpCodeTableElement;
import com.github.ConcordiaSOEN341.CodeGen.SymbolTable;
import com.github.ConcordiaSOEN341.Error.Error;
import com.github.ConcordiaSOEN341.Error.IErrorReporter;
import com.github.ConcordiaSOEN341.Lexer.ILexer;
import com.github.ConcordiaSOEN341.Lexer.IToken;
import com.github.ConcordiaSOEN341.Lexer.Position;
import com.github.ConcordiaSOEN341.Lexer.TokenType;
import com.github.ConcordiaSOEN341.Logger.ILogger;
import com.github.ConcordiaSOEN341.Logger.LoggerFactory;
import com.github.ConcordiaSOEN341.Logger.LoggerType;

import java.util.ArrayList;

public class Parser implements IParser {
    private ArrayList<ILineStatement> intermediateRep = new ArrayList<>();
    private final ArrayList<IOpCodeTableElement> opCodeTable = new ArrayList<>();
    private final ILexer lexer;
    private final ParserFSM parserFSM;
    private final IErrorReporter reporter;
    private final SymbolTable symbolTable;
    private final ILogger logger;

    public Parser(ParserFSM p, ILexer l, SymbolTable sT, LoggerFactory lf, IErrorReporter e) {
        logger = lf.getLogger(LoggerType.PARSER);
        logger.log("Initializing Parser");
        parserFSM = p;
        lexer = l;
        reporter = e;
        symbolTable = sT;
    }

    public Parser(SymbolTable sT, LoggerFactory lf, IErrorReporter e) {
        logger = lf.getLogger(LoggerType.PARSER);
        logger.log("Initializing Parser");
        parserFSM = null;
        lexer = null;
        reporter = e;
        symbolTable = sT;
    }

    @Override
    public ArrayList<ILineStatement> getIR(){
        return intermediateRep;
    }

    @Override
    public void setIR(ArrayList<ILineStatement> ir){
        intermediateRep = ir;
    }

    @Override
    public ArrayList<IOpCodeTableElement> getOpCodeTable(){
        return opCodeTable;
    }

    @Override
    public void parse() {
        logger.log("Orchestration has started");
        // ORCHESTRATE
        logger.log("Orchestration - generating intermediate representation...");
        generateIR();
        logger.log("Orchestration - intermediate representation generated");

        logger.log("Orchestration - generating opcode table...");
        generateOpCodeTable();
        logger.log("Orchestration - opcode table generated");
    }

    @Override
    public ArrayList<ILineStatement> generateIR() {
        ILineStatement lStatement;
        IToken t;
        IToken temp = null;
        int stateID;

        do {
            stateID = parserFSM.getInitialStateID();
            lStatement = new LineStatement();

            do {
                if (temp == null) {
                    t = lexer.getNextToken();
                } else {
                    t = temp;
                    temp = null;
                }

                if (parserFSM.getNextStateID(stateID, t.getTokenType()) == 0) {
                    temp = (t.getTokenType() != TokenType.OFFSET && t.getTokenType() != TokenType.LABEL) ? t : null;
                    reporter.record(new Error(parserFSM.getErrorType(stateID), t.getPosition()));
                    stateID = 1;
                } else {
                    switch (t.getTokenType()) {
                        case LABEL:
                            if (stateID == 1) {
                                lStatement.setLabel(t);
                            } else if (stateID == 3) {
                                lStatement.getInstruction().setOperand(t);
                            }
                            break;
                        case MNEMONIC:
                            lStatement.setInstruction(new Instruction(t, getAddressingMode(t)));
                            if (lStatement.getInstruction().getInstructionType() == InstructionType.INHERENT) {
                                stateID = 4;
                                continue;
                            }
                            break;
                        case OFFSET:
                            checkInstructionSpace(lStatement.getInstruction(), t);
                            lStatement.getInstruction().setOperand(t);
                            break;
                        case DIRECTIVE:
                            lStatement.setDirective(new Directive(t));
                            if(!t.getTokenString().equals(".cstring"))
                                reporter.record(new Error(t.getTokenString(), parserFSM.getErrorType(66), t.getPosition()));
                            break;
                        case CSTRING:
                            lStatement.getDirective().setCString(t);
                            break;
                        case COMMENT:
                            lStatement.setComment(t);
                            break;
                        case EOF:
                            lStatement.setEOL(t);
                            break;
                        case EOL:
                            lStatement.setEOL(t);
                            break;
                        default:
                            break;
                    }
                    stateID = parserFSM.getNextStateID(stateID, t.getTokenType());
                }


            } while (stateID != parserFSM.getFinalStateID());

            intermediateRep.add(lStatement);

        } while (t.getTokenType() != TokenType.EOF);

        lexer.closeReader();
        return intermediateRep;
    }

    @Override
    public ArrayList<IOpCodeTableElement> generateOpCodeTable() {
        int line = 1;
        int address = 0;
        logger.log("Generating OpCodeTable");
        logger.log("Starting First Pass now...");
        // FIRST PASS
        for (ILineStatement lS : intermediateRep) {
            IOpCodeTableElement oTE = new OpCodeTableElement();

            // Set Line and Address
            oTE.setLine(line);
            oTE.setAddress(String.format("%04X", address));

            // Add Label - Address to symbol table
            if (lS.getLabel().getTokenType() != TokenType.ERROR || lS.getLabel() != null) {
                if(symbolTable.keyExists(lS.getLabel().getTokenString()))
                    reporter.record(new Error(lS.getLabel().getTokenString(), parserFSM.getErrorType(420), new Position(oTE.getLine())));
                else if (!lS.getLabel().getTokenString().equals(""))
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

                // check if operand should be a label
                if(isLabelOperand(lS.getInstruction().getMnemonic().getTokenString()) && oTE.getLabel() == null){
                    reporter.record(new Error(parserFSM.getErrorType(42069), new Position(oTE.getLine())));
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
            // only check for recorded labels that are not empty strings
            if (oTE.getLabel() != null && !oTE.getLabel().equals("")) {
                String labelAddress = symbolTable.getValue(oTE.getLabel());
                if (labelAddress != null) {
                    int offset = Integer.parseInt(labelAddress, 16) - Integer.parseInt(oTE.getAddress(), 16);
                    if (offset < 0) {
                        String offsetString = String.format("%X", offset);
                        oTE.addOperand(offsetString.substring(offsetString.length() - oTE.getBitSpace()));
                    } else {
                        oTE.addOperand(String.format("%0" + oTE.getBitSpace() + "X", offset));
                    }
                } else {
                    reporter.record(new Error(oTE.getLabel(), parserFSM.getErrorType(69), new Position(oTE.getLine(), 0,0)));
                }
            }
        }
        logger.log("Completed Second Pass...");
        return opCodeTable;
    }

    private boolean isLabelOperand(String mne){
        return mne.contains("br") || mne.contains("lda");
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

    private void checkInstructionSpace(IInstruction in, IToken t) {
        char sign = in.getMnemonic().getTokenString().charAt(in.getMnemonic().getTokenString().indexOf('.') + 1);
        int bitSpace = getSymbolValue(in.getMnemonic());
        int operand;
        try {
            operand = Integer.parseInt(t.getTokenString());
        } catch (NumberFormatException e) {
            return;
        }
        if (sign == 'i' && (-Math.pow(2, bitSpace - 1) > operand || operand >= Math.pow(2, bitSpace - 1))) {
            reporter.record(new Error(parserFSM.getErrorType((bitSpace * 3) + 1), t.getPosition()));

        }
        if (sign == 'u' && (operand < 0 || operand >= Math.pow(2, bitSpace))) {
            reporter.record(new Error(parserFSM.getErrorType((bitSpace * 3) + 2), t.getPosition()));
        }
    }

    private int getSymbolValue(IToken token) {
        int value = Integer.parseInt(token.getTokenString().split(".((u)|(i))", 2)[1]);
        logger.log("Getting symbol value " + value);
        return value;
    }

    private InstructionType getAddressingMode(IToken token) {
        logger.log("Getting addressing mode");
        if (!(token.getTokenString().contains("."))) {                      //no dot it's definitely inherent
            return InstructionType.INHERENT;
        }
        //take string after the u or the i (this leaves only the number)
        //less than or equal to 1 byte is immediate
        return (getSymbolValue(token) < 8) ? InstructionType.IMMEDIATE : InstructionType.RELATIVE;
    }
}
