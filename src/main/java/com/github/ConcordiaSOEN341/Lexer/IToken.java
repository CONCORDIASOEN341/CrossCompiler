package com.github.ConcordiaSOEN341.Lexer;

import com.github.ConcordiaSOEN341.Lexer.IPosition;
import com.github.ConcordiaSOEN341.Lexer.TokenType;

public interface IToken {
    IPosition getPosition();

    String getTokenString();

    TokenType getTokenType();
}
