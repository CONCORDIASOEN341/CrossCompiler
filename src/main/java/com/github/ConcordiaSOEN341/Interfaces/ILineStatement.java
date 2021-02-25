package com.github.ConcordiaSOEN341.Interfaces;

import com.github.ConcordiaSOEN341.Lexer.Token;
import com.github.ConcordiaSOEN341.Parser.Instruction;

public interface ILineStatement {
    IInstruction getInstruction();
    IToken getEOL();
}
