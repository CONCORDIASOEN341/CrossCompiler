package com.github.ConcordiaSOEN341.Parser;

import com.github.ConcordiaSOEN341.Interfaces.IInstruction;
import com.github.ConcordiaSOEN341.Interfaces.IToken;

public class Instruction implements IInstruction {
    private IToken mnemonic;
    private IToken label;       //one of these two is the operand
    private IToken offset;      //
    private InstructionType instructionType;

    public Instruction() {
        mnemonic = null;
        label = null;
        offset = null;
        instructionType = null;
    }

    public IToken getMnemonic() {
        return mnemonic;
    }

    public void setMnemonic(IToken mnemonic) {
        this.mnemonic = mnemonic;
    }

    public IToken getLabel() {
        return label;
    }

    public void setLabel(IToken label) {
        this.label = label;
    }

    public IToken getOffset() {
        return offset;
    }

    public void setOffset(IToken offset) {
        this.offset = offset;
    }

    public InstructionType getInstructionType() {
        return instructionType;
    }

    public void setInstructionType(InstructionType instructionType) {
        this.instructionType = instructionType;
    }

    public String toString() { return mnemonic.getTokenString();}


}