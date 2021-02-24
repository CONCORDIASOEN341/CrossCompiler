package com.github.ConcordiaSOEN341.Interfaces;

import com.github.ConcordiaSOEN341.Lexer.TokenType;

public interface IToken {
    int getLine();
    int getStartColumn();
    int getEndColumn();
    String getTokenString();
    TokenType getTokenType();
}
