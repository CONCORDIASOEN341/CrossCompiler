package com.github.ConcordiaSOEN341.Parser;

import com.github.ConcordiaSOEN341.CodeGen.CodeGen;
import com.github.ConcordiaSOEN341.Interfaces.*;
import com.github.ConcordiaSOEN341.Lexer.Lexer;
import com.github.ConcordiaSOEN341.Lexer.Token;
import com.github.ConcordiaSOEN341.Lexer.TokenType;
import com.github.ConcordiaSOEN341.Reader.Reader;

import java.util.ArrayList;

public class Parser implements IParser {
    private final ArrayList<ILineStatement> intermediateRep;
    private final ILexer lexer;

    public Parser(ILexer l) {
        lexer = l;
        intermediateRep = new ArrayList<>();
    }

    public ArrayList<ILineStatement> parse(){
        ArrayList<IToken> tokenList = lexer.generateTokenList();

        Instruction inst = null;
        for(IToken t : tokenList) {
            if (t.getTokenType() == TokenType.MNEMONIC) {
                inst = new Instruction(t);
            } else if (t.getTokenType() == TokenType.EOL) {
                LineStatement lStatement = new LineStatement(inst, t);
                intermediateRep.add(lStatement);
            }
        }
        return intermediateRep;

    }

    public ArrayList<ILineStatement> getIntermediateRep() {
        return intermediateRep;
    }

    // DEPRECATED: REPLACED BY PARSE()
    public ArrayList<ILineStatement> generateIR(ArrayList<IToken> tList) {
        Instruction inst = null;
        for(IToken t : tList) {
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
