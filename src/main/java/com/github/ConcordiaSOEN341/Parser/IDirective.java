package com.github.ConcordiaSOEN341.Parser;

import com.github.ConcordiaSOEN341.Lexer.IToken;

public interface IDirective {
    IToken getDir();

    IToken getCString();

    void setCString(IToken t);
}
