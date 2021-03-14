package com.github.ConcordiaSOEN341.Parser;

import com.github.ConcordiaSOEN341.Interfaces.*;
import com.github.ConcordiaSOEN341.Lexer.Token;
import com.github.ConcordiaSOEN341.Lexer.TokenType;

import javax.sound.sampled.Line;
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

        Instruction inst = null;
        IToken cstring = null;
        IToken comment = null;
        LineStatement lStatement = new LineStatement();
        for (IToken t : tokenList) {
            inst = new Instruction(t);

            if (t.getTokenType() == TokenType.MNEMONIC) { //MNEMONIC currently doesn't include anything with the .

            } else if (t.getTokenType() == TokenType.CSTRING) {
                cstring = t;
                lStatement.setDirective(cstring);
            } else if (t.getTokenType() == TokenType.COMMENT) {
                comment = t;
                lStatement.setComment(comment);
            } else if (t.getTokenType() == TokenType.EOL) {
                intermediateRep.add(lStatement);

            }
        }
        return intermediateRep;

    }

    /*
        Split mnemonic at the dot, after the dot parse the u3 or u5 etc, based on that determine addressing mode
     */

    public InstructionType checkAddressingMode(IToken t) {
        String temp [] = t.getTokenString().split("\\.");


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
