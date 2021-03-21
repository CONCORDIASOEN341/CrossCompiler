package com.github.ConcordiaSOEN341.Interfaces;

import com.github.ConcordiaSOEN341.Parser.LineStatement;

public interface ILineStatement {
    IInstruction getInstruction();
    IToken getEOL();
    IToken getDirective();
    IToken getComment();
}
