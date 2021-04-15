package com.github.ConcordiaSOEN341.Parser;

import com.github.ConcordiaSOEN341.Error.Error;
import com.github.ConcordiaSOEN341.Interfaces.*;
import com.github.ConcordiaSOEN341.Lexer.TokenType;
import com.github.ConcordiaSOEN341.Logger.LoggerFactory;
import com.github.ConcordiaSOEN341.Logger.LoggerType;

import java.util.ArrayList;

public class Parser implements IParser {
    private final ArrayList<ILineStatement> intermediateRep = new ArrayList<>();
    private final ILexer lexer;
    private final ParserFSM parserFSM;
    private final IErrorReporter reporter;
    private final ICodeGen generator;
    private final ILogger logger;

    public Parser(ParserFSM p, ILexer l, ICodeGen g, LoggerFactory lf, IErrorReporter e) {
        logger = lf.getLogger(LoggerType.PARSER);
        logger.log("Initializing Parser");
        parserFSM = p;
        lexer = l;
        reporter = e;
        generator = g;
    }

    public void parse(String fileName) {
        logger.log("Orchestration has started");
        // ORCHESTRATE
        generateIR();

        logger.log("Orchestration - Checking with reporter for errors...");
        if (reporter.hasErrors()) {
            System.out.println(reporter.report(fileName));
            System.exit(0);
        }

        logger.log("Orchestration - Setting IR to intermediate representation...");
        generator.setIR(intermediateRep);
        logger.log("Orchestration - IR has been set to intermediate representation");

        logger.log("Orchestration - generating opcode table...");
        generator.generateOpCodeTable();
        logger.log("Orchestration - opcode table generated");

        logger.log("Orchestration - generating executable...");
        generator.generateExe(fileName);
        logger.log("Orchestration - executable has been generated");

        logger.log("Orchestration - generating listing file...");
        generator.generateListingFile(fileName);
        logger.log("Orchestration - listing file has been generated");
    }

    public ArrayList<ILineStatement> generateIR() {
        ILineStatement lStatement;
        IToken t;
        IToken temp = null;
        int stateID;

        do {
            stateID = 1;
            lStatement = new LineStatement();

            do {
                if (temp == null) {
                    t = lexer.getNextToken();
                } else {
                    t = temp;
                    temp = null;
                }

                if(parserFSM.getNextStateID(stateID, t.getTokenType()) == 0){
                    temp = (t.getTokenType() != TokenType.OFFSET && t.getTokenType() != TokenType.LABEL)? t : null;
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
                            // logger.log("Directive created: " + this);
                            lStatement.setDirective(new Directive(t));
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


            } while (stateID != 7);

            intermediateRep.add(lStatement);

        } while (t.getTokenType() != TokenType.EOF);

        return intermediateRep;
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
