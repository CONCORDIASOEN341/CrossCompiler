package com.github.ConcordiaSOEN341.Interfaces;

public interface IDirective {
    IToken getDir();

    IToken getCString();

    void setCString(IToken t);
}
