package com.github.ConcordiaSOEN341.Interfaces;

import com.github.ConcordiaSOEN341.Lexer.TokenType;

public interface IToken {
    IPosition getPosition();

    String getTokenString();

    TokenType getTokenType();
}
