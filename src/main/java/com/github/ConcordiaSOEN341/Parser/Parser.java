package com.github.ConcordiaSOEN341.Parser;

import com.github.ConcordiaSOEN341.Interfaces.ILineStatement;
import com.github.ConcordiaSOEN341.Interfaces.IParser;
import com.github.ConcordiaSOEN341.Interfaces.IToken;
import com.github.ConcordiaSOEN341.Lexer.TokenType;

import java.util.ArrayList;

public class Parser implements IParser {
    private final ArrayList<ILineStatement> intermediateRep;

    public Parser() {
        this.intermediateRep = new ArrayList<>();
    }

    public ArrayList<ILineStatement> getIntermediateRep() {
        return intermediateRep;
    }

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
