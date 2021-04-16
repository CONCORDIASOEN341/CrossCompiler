package com.github.ConcordiaSOEN341.Parser;

import com.github.ConcordiaSOEN341.Lexer.IToken;

public interface IInstruction {
    IToken getMnemonic();

    InstructionType getInstructionType();

    IToken getOperand();

    void setOperand(IToken operand);
}
