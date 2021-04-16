package com.github.ConcordiaSOEN341.Lexer;

public interface ILexer {
    IToken getNextToken();

    void closeReader();
}
