package com.github.ConcordiaSOEN341.Interfaces;

import com.github.ConcordiaSOEN341.Lexer.Token;

import java.util.ArrayList;

public interface ILexer {
    ArrayList<IToken> generateTokenList();
}
