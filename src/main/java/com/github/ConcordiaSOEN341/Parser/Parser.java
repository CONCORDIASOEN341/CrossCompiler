package com.github.ConcordiaSOEN341.Parser;

import com.github.ConcordiaSOEN341.Lexer.Token;
import com.github.ConcordiaSOEN341.Lexer.TokenType;

import java.util.ArrayList;

public class Parser {
    private final ArrayList<LineStatement> intermediateRep;

    public Parser() {
        this.intermediateRep = new ArrayList<>();
    }

    public ArrayList<LineStatement> getIntermediateRep() {
        return intermediateRep;
    }

    /* Create the IR here
           1. get arraylist of tokens (from driver)
           2. loop through tlist while building instruction object
           3. @ EOL or EOF create line statement obj.
           4. Add line statement to IR
           5. @ EOF return the IR
        */
    public ArrayList<LineStatement> generateIR(ArrayList<Token> tList) {
        Instruction inst = null;
        for(Token t : tList) {
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
