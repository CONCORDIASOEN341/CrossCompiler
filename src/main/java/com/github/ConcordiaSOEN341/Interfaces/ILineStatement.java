package com.github.ConcordiaSOEN341.Interfaces;

public interface ILineStatement {

    IToken getLabel();

    IInstruction getInstruction();

    IToken getEOL();

    IDirective getDirective();

    IToken getComment();
}
