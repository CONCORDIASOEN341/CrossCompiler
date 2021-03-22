package com.github.ConcordiaSOEN341.Interfaces;

public interface ILineStatement {
    IInstruction getInstruction();

    IToken getEOL();

    IToken getDirective();

    IToken getComment();
}
