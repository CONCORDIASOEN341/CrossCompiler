package com.github.ConcordiaSOEN341.Lexer;

import com.github.ConcordiaSOEN341.Interfaces.ILexer;
import com.github.ConcordiaSOEN341.Interfaces.IToken;

import java.util.ArrayList;

public class LexerMoq implements ILexer {
    private final ArrayList<IToken> tokenList;

    public LexerMoq(ArrayList<IToken> tL) {
        tokenList = tL;
    }

    @Override
    public ArrayList<IToken> generateTokenList() {
        return tokenList;
    }
}
