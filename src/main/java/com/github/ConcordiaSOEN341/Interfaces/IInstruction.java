package com.github.ConcordiaSOEN341.Interfaces;

import com.github.ConcordiaSOEN341.Parser.InstructionType;

public interface IInstruction {
    IToken getMnemonic();

    InstructionType getInstructionType();

    IToken getOperand();
}
