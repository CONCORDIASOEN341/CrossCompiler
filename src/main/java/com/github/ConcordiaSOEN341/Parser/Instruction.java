package com.github.ConcordiaSOEN341.Parser;

import com.github.ConcordiaSOEN341.Interfaces.IInstruction;
import com.github.ConcordiaSOEN341.Interfaces.IToken;

public class Instruction implements IInstruction {
    private IToken mnemonic;
    private IToken operand;

    public Instruction(IToken mnemonic, InstructionType instructionType) {
        this.mnemonic = mnemonic;
        this.instructionType = instructionType;
    }

    public Instruction(IToken mnemonic, IToken operand, InstructionType instructionType) {
        this.mnemonic = mnemonic;
        this.operand = operand;
        this.instructionType = instructionType;
    }

    private InstructionType instructionType;

    public Instruction() {
        mnemonic = null;
        operand = null;
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

    public String toString() { return mnemonic.getTokenString();}


}