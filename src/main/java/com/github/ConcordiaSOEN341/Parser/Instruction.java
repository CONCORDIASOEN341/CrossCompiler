package com.github.ConcordiaSOEN341.Parser;

import com.github.ConcordiaSOEN341.Lexer.IToken;
import com.github.ConcordiaSOEN341.Lexer.Token;

public class Instruction implements IInstruction {
    private IToken mnemonic;
    private IToken operand;
    private InstructionType instructionType;

    public Instruction(IToken mnemonic, InstructionType instructionType) {
        this.mnemonic = mnemonic;
        this.instructionType = instructionType;
        this.operand = new Token();
    }

    public Instruction(IToken mnemonic, IToken operand, InstructionType instructionType) {
        this.mnemonic = mnemonic;
        this.operand = operand;
        this.instructionType = instructionType;
    }

    public Instruction() {
        mnemonic = new Token();
        operand = new Token();
        instructionType = null;
    }

    public IToken getMnemonic() {
        return mnemonic;
    }

    public void setMnemonic(IToken mnemonic) {
        this.mnemonic = mnemonic;
    }

    public IToken getOperand() {
        return operand;
    }

    public void setOperand(IToken operand) {
        this.operand = operand;
    }

    public InstructionType getInstructionType() {
        return instructionType;
    }

    public void setInstructionType(InstructionType instructionType) {
        this.instructionType = instructionType;
    }

    public String toString() {
        return mnemonic.getTokenString() + " " + operand.getTokenString();
    }


}