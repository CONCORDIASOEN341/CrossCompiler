package com.github.ConcordiaSOEN341.Parser;

import com.github.ConcordiaSOEN341.Error.Error;
import com.github.ConcordiaSOEN341.Interfaces.*;
import com.github.ConcordiaSOEN341.Lexer.TokenType;

import java.util.ArrayList;

public class Parser2 implements IParser {
    private final ArrayList<ILineStatement> intermediateRep = new ArrayList<>();;
    private final ILexer lexer;
    private final ParserFSM parserFSM;
    private final IErrorReporter reporter;
    private final ICodeGen generator;

    public Parser2(ParserFSM p, ILexer l, ICodeGen g, IErrorReporter e) {
        parserFSM = p;
        lexer = l;
        reporter = e;
        generator = g;
    }

    public void parse(String fileName){
        // ORCHESTRATE
        generateIR();
        generator.setIR(intermediateRep);
        generator.generateOpCodeTable();

        generator.generateExe(fileName);
        generator.generateListingFile(fileName);
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
                if(temp == null){
                    t = lexer.getNextToken();
                } else {
                    t = temp;
                    temp = null;
                }

                if(parserFSM.getNextStateID(stateID, t.getTokenType()) == 0){
                    temp = t;
                    reporter.record(new Error(parserFSM.getErrorType(stateID), t.getPosition()));
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
                            if(lStatement.getInstruction().getInstructionType() == InstructionType.INHERENT){
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
                }

                stateID = parserFSM.getNextStateID(stateID, t.getTokenType());
            } while (stateID != 7);

            intermediateRep.add(lStatement);

        } while (t.getTokenType() != TokenType.EOF);

        return intermediateRep;
    }

    private void checkInstructionSpace(IInstruction in, IToken t){
        char sign = in.getMnemonic().getTokenString().charAt(in.getMnemonic().getTokenString().indexOf('.')+1);
        int bitSpace = getSymbolValue(in.getMnemonic());
        int operand;
        try{
            operand = Integer.parseInt(t.getTokenString());
        } catch (NumberFormatException e){
            return;
        }
        if(sign == 'i' && (-Math.pow(2,bitSpace-1) > operand || operand >= Math.pow(2,bitSpace-1))){
            reporter.record(new Error(parserFSM.getErrorType((bitSpace*3)+1), t.getPosition()));

        }
        if(sign == 'u' && (operand < 0 || operand >= Math.pow(2,bitSpace))){
            reporter.record(new Error(parserFSM.getErrorType((bitSpace*3)+2), t.getPosition()));
        }
    }

    private int getSymbolValue(IToken token){
        return Integer.parseInt(token.getTokenString().split(".((u)|(i))", 2)[1]);
    }

    private InstructionType getAddressingMode(IToken token){
        if (!(token.getTokenString().contains("."))) {                      //no dot it's definitely inherent
            return InstructionType.INHERENT;
        }
        //take string after the u or the i (this leaves only the number)
        //less than or equal to 1 byte is immediate
        return (getSymbolValue(token) < 8)? InstructionType.IMMEDIATE : InstructionType.RELATIVE;
    }
}
