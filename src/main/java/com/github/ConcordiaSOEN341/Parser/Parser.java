package com.github.ConcordiaSOEN341.Parser;

import com.github.ConcordiaSOEN341.Interfaces.*;
import com.github.ConcordiaSOEN341.Lexer.TokenType;
import java.util.ArrayList;

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
            if (currentLine > line){                                     //create new line statement + instruction per line
                line = currentLine;
                lStatement = new LineStatement();
                instruction = new Instruction();
            }
            if (t.getTokenType() == TokenType.MNEMONIC) {
                instruction.setMnemonic(t);
                instruction.setInstructionType(checkAddressingMode(t));
                lStatement.setInstruction(instruction);
            } else if (t.getTokenType() == TokenType.LABEL) {
                instruction.setLabel(t);
                lStatement.setInstruction(instruction);
            } else if (t.getTokenType() == TokenType.OFFSET) {
                instruction.setOffset(t);
            } else if (t.getTokenType() == TokenType.CSTRING) {
                lStatement.setDirective(t);
            } else if (t.getTokenType() == TokenType.COMMENT) {
                lStatement.setComment(t);
            } else if (t.getTokenType() == TokenType.EOL) {
                intermediateRep.add(lStatement);
            }
        }
        return intermediateRep;

    }

    public InstructionType checkAddressingMode(IToken t) {
        if (!(t.getTokenString().contains("."))) {                      //no dot it's definitely inherent
            return InstructionType.INHERENT;
        }
        String temp = t.getTokenString();
        String[] sNum = temp.split("(u)|(i)", 2);            //take string after the u or the i (this leaves only the number)
        int num = Integer.parseInt(sNum[1]);
        if (num <= 8 ){                                                 //less than or equal to 1 byte is immediate
            return InstructionType.IMMEDIATE;
        } else {
            return InstructionType.RELATIVE;
        }
    }

    public ArrayList<ILineStatement> getIntermediateRep() {
        return intermediateRep;
    }

    @Deprecated
    public ArrayList<ILineStatement> generateIR(ArrayList<IToken> tList) {
        Instruction inst = null;
        for (IToken t : tList) {
            if (t.getTokenType() == TokenType.MNEMONIC) {
                inst = new Instruction(t);
            } else if (t.getTokenType() == TokenType.EOL) {
                LineStatement lStatement = new LineStatement(inst, t);
                intermediateRep.add(lStatement);
            }
        }
        return intermediateRep;
    }


}
