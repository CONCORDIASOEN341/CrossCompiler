package com.github.ConcordiaSOEN341.Parser;

import com.github.ConcordiaSOEN341.Interfaces.*;
import com.github.ConcordiaSOEN341.Lexer.Token;
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

        Instruction instruction = null;
        IToken cstring = null;
        IToken comment = null;
        LineStatement lStatement = new LineStatement();
        for (IToken t : tokenList) {
            instruction = new Instruction(t);

            if (t.getTokenType() == TokenType.MNEMONIC) {
                instruction.setMnemonic(t);
                instruction.setInstructionType(checkAddressingMode(t));
            } else if (t.getTokenType() == TokenType.LABEL) {
                instruction.setLabel(t);
            } else if (t.getTokenType() == TokenType.OFFSET) {
                instruction.setOffset(t);
            } else if (t.getTokenType() == TokenType.CSTRING) {
                cstring = t;
            } else if (t.getTokenType() == TokenType.COMMENT) {
                comment = t;
            } else if (t.getTokenType() == TokenType.EOL) {
                lStatement.setInstruction(instruction);
                lStatement.setDirective(cstring);
                lStatement.setComment(comment);

                intermediateRep.add(lStatement);
                instruction = null;
                cstring = null;
                comment = null;
            }
        }
        return intermediateRep;

    }

    public InstructionType checkAddressingMode(IToken t) {
        String temp [] = t.getTokenString().split("\\.");
        System.out.println(temp[0]);


        return InstructionType.INHERENT;

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
