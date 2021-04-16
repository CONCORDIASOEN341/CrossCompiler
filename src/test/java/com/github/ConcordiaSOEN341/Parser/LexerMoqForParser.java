package com.github.ConcordiaSOEN341.Parser;

import com.github.ConcordiaSOEN341.Lexer.ILexer;
import com.github.ConcordiaSOEN341.Lexer.IToken;

import java.util.ArrayList;
import java.util.Iterator;

public class LexerMoqForParser implements ILexer {
    private final Iterator<IToken> tokenList;

    public LexerMoqForParser(ArrayList<IToken> tL) {
        tokenList = tL.iterator();
    }

    @Override
    public IToken getNextToken(){
        return tokenList.next();
    }

    @Override
    public void closeReader() {

    }


}
