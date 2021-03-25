package com.github.ConcordiaSOEN341.Parser;

import com.github.ConcordiaSOEN341.Error.Error;
import com.github.ConcordiaSOEN341.Error.ErrorReporter;
import com.github.ConcordiaSOEN341.Error.ErrorType;
import com.github.ConcordiaSOEN341.Interfaces.ILexer;
import com.github.ConcordiaSOEN341.Interfaces.ILineStatement;
import com.github.ConcordiaSOEN341.Interfaces.IParser;
import com.github.ConcordiaSOEN341.Interfaces.IToken;
import com.github.ConcordiaSOEN341.Lexer.Position;
import com.github.ConcordiaSOEN341.Lexer.Token;
import com.github.ConcordiaSOEN341.Lexer.TokenType;

import java.util.ArrayList;

import static com.github.ConcordiaSOEN341.Lexer.TokenType.EOL;

public class Parser implements IParser {
    private final ArrayList<ILineStatement> intermediateRep;
    private final ILexer lexer;


    public Parser(ILexer l) {
        lexer = l;
        intermediateRep = new ArrayList<>();
    }

    public ArrayList<ILineStatement> parse() {
        ArrayList<IToken> tokenList = lexer.generateTokenList();

        int line = -1;
        Instruction instruction = null;
        LineStatement lStatement = null;
        for (IToken t : tokenList) {
            int currentLine = t.getPosition().getLine();
            if (currentLine > line) {                                     //create new line statement + instruction per line
                line = currentLine;
                instruction = new Instruction(new Token("", new Position(0, 0, 0), TokenType.EMPTY), new Token("", new Position(0, 0, 0), TokenType.EMPTY), new Token("", new Position(0, 0, 0), TokenType.EMPTY), InstructionType.EMPTY);
                lStatement = new LineStatement(instruction, new Token("", new Position(0, 0, 0), TokenType.DIRECTIVE), new Token("", new Position(0, 0, 0), TokenType.OFFSET), new Token("", new Position(0, 0, 0), TokenType.COMMENT), new Token("", new Position(0, 0, 0), TokenType.EOL));
            }
            if (t.getTokenType() == TokenType.EMPTY) {
                instruction.setInstructionType(InstructionType.EMPTY);
            } else if (t.getTokenType() == TokenType.MNEMONIC) {
                instruction.setMnemonic(t);
                instruction.setInstructionType(checkAddressingMode(t));
                lStatement.setInstruction(instruction);
            } else if (t.getTokenType() == TokenType.IDENTIFIER) {

                instruction.setMnemonic(t);
                instruction.setInstructionType(checkAddressingMode(t));
                lStatement.setInstruction(instruction);

            } else if (t.getTokenType() == TokenType.LABEL) {
                instruction.setLabel(t);
                lStatement.setInstruction(instruction);
            } else if (t.getTokenType() == TokenType.OFFSET) {
                instruction.setOffset(t);
                lStatement.setOffset(t);
            } else if (t.getTokenType() == TokenType.CSTRING) {
                lStatement.setDirective(t);
            } else if (t.getTokenType() == TokenType.COMMENT) {
                lStatement.setComment(t);
            } else if (t.getTokenType() == EOL) {
                lStatement.setEOL(t);
                if (isValid(lStatement)) {
                    intermediateRep.add(lStatement);
                }
            }
        }

        return intermediateRep;

    }

    public InstructionType checkAddressingMode(IToken t) {
        if (!(t.getTokenString().contains("."))) {                      //no dot it's definitely inherent
            return InstructionType.INHERENT;
        }
        int num = getInt(t);
        if (num <= 8) {                                                 //less than or equal to 1 byte is immediate
            return InstructionType.IMMEDIATE;
        } else {
            return InstructionType.RELATIVE;
        }
    }

    private int getInt(IToken t) {
        String temp = t.getTokenString();
        String[] sNum = temp.split("(u)|(i)", 2);            //take string after the u or the i (this leaves only the number)
        return Integer.parseInt(sNum[1]);
    }

    //check inherent and immediate instructions
    private boolean isValid(LineStatement lineStatement) {
        int currentLine = 0;
        int currentColumn = 0;

        if (lineStatement.getInstruction().getInstructionType() == InstructionType.EMPTY) {
            return true;
        }

        if (lineStatement.getInstruction() != null) {
            currentLine = lineStatement.getInstruction().getMnemonic().getPosition().getLine();
            currentColumn = lineStatement.getInstruction().getMnemonic().getPosition().getStartColumn();

            if (lineStatement.getInstruction().getInstructionType() == InstructionType.INHERENT) {
                if (lineStatement.getInstruction().getOffset().getTokenType() == TokenType.EMPTY) {
                    return true;
                } else {
                    ErrorReporter.record(new Error(ErrorType.EXTRA_OPERAND, new Position(currentLine, currentColumn, currentColumn + 1)));
                    return false;
                }
            }

            //check all immediate instruction possibilities
            if (lineStatement.getInstruction().getInstructionType() == InstructionType.IMMEDIATE) {
                //immediate without a value is instantly not good
                if (lineStatement.getInstruction().getOffset().getTokenType() == TokenType.EMPTY) {
                    ErrorReporter.record(new Error(ErrorType.MISSING_OPERAND, new Position(currentLine, currentColumn, currentColumn + 1)));
                    return false;
                }

                int opSize = getInt(lineStatement.getInstruction().getMnemonic());              //get operator value
                String temp = lineStatement.getInstruction().getMnemonic().getTokenString();
                String symbol = temp.substring(temp.indexOf('.') + 1, temp.indexOf('.') + 2);   //get i or u
                String op = lineStatement.getInstruction().getOffset().getTokenString();        //get operand value (String)
                int opNum = Integer.parseInt(op);                                               //get operand value (int)

                //SIGNED
                if (symbol.contains("i")) {
                    if (opSize == 3) {   //i3
                        if (opNum < -4 || opNum > 3) {
                            ErrorReporter.record(new Error(ErrorType.INVALID_SIGNED_3BIT_OPERAND, new Position(currentLine, currentColumn, currentColumn + 1)));
                            return false;
                        } else {
                            return true;
                        }
                    } else if (opSize == 4) {    //i4
                        if (opNum < -8 || opNum > 7) {
                            ErrorReporter.record(new Error(ErrorType.INVALID_SIGNED_4BIT_OPERAND, new Position(currentLine, currentColumn, currentColumn + 1)));
                            return false;
                        } else {
                            return true;
                        }
                    } else if (opSize == 5) {    //i5
                        if (opNum < -16 || opNum > 15) {
                            ErrorReporter.record(new Error(ErrorType.INVALID_SIGNED_5BIT_OPERAND, new Position(currentLine, currentColumn, currentColumn + 1)));
                            return false;
                        } else {
                            return true;
                        }
                    } else if (opSize == 8) {    //i8
                        if (opNum < -128 || opNum > 127) {
                            ErrorReporter.record(new Error(ErrorType.INVALID_SIGNED_8BIT_OPERAND, new Position(currentLine, currentColumn, currentColumn + 1)));
                            return false;
                        } else {
                            return true;
                        }
                    }
                    //UNSIGNED
                } else if (symbol.contains("u")) {
                    if (opSize == 3) {   //u3
                        if (opNum < 0 || opNum > 7) {
                            ErrorReporter.record(new Error(ErrorType.INVALID_UNSIGNED_3BIT_OPERAND, new Position(currentLine, currentColumn, currentColumn + 1)));
                            return false;
                        } else {
                            return true;
                        }
                    } else if (opSize == 4) {    //u4
                        if (opNum < 0 || opNum > 15) {
                            ErrorReporter.record(new Error(ErrorType.INVALID_UNSIGNED_4BIT_OPERAND, new Position(currentLine, currentColumn, currentColumn + 1)));
                            return false;
                        } else {
                            return true;
                        }
                    } else if (opSize == 5) {    //u5
                        if (opNum < 0 || opNum > 31) {
                            ErrorReporter.record(new Error(ErrorType.INVALID_UNSIGNED_5BIT_OPERAND, new Position(currentLine, currentColumn, currentColumn + 1)));
                            return false;
                        } else {
                            return true;
                        }
                    } else if (opSize == 8) {    //u8
                        if (opNum < 0 || opNum > 255) {
                            ErrorReporter.record(new Error(ErrorType.INVALID_UNSIGNED_8BIT_OPERAND, new Position(currentLine, currentColumn, currentColumn + 1)));
                            return false;
                        } else {
                            return true;
                        }
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<ILineStatement> getIntermediateRep() {
        return intermediateRep;
    }


}
