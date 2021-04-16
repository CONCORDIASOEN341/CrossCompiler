package com.github.ConcordiaSOEN341.Parser;

import com.github.ConcordiaSOEN341.Interfaces.ILexer;
import com.github.ConcordiaSOEN341.Interfaces.IReader;
import com.github.ConcordiaSOEN341.Interfaces.IToken;

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
