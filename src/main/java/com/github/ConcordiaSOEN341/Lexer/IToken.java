package com.github.ConcordiaSOEN341.Lexer;

public interface IToken {
    IPosition getPosition();

    String getTokenString();

    TokenType getTokenType();
}
