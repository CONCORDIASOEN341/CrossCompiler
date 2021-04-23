package com.github.ConcordiaSOEN341.Parser;

import com.github.ConcordiaSOEN341.Lexer.IToken;

public interface IInstruction {
    IToken getMnemonic();

    void setMnemonic(IToken mnemonic);

    InstructionType getInstructionType();

    void setInstructionType(InstructionType instructionType);

    IToken getOperand();

    void setOperand(IToken operand);
}
