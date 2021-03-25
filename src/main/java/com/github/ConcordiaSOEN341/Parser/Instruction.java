package com.github.ConcordiaSOEN341.Parser;

import com.github.ConcordiaSOEN341.Interfaces.IInstruction;
import com.github.ConcordiaSOEN341.Interfaces.IToken;

//For Sprint 2, instruction is only a mnemonic token
public class Instruction implements IInstruction {
    private IToken mnemonic;
    private IToken label;       //one of these two is the operand
    private IToken offset;      //
    private InstructionType instructionType;

    public Instruction() {
        IToken mnemonic = null;
        IToken label = null;
        IToken offset = null;
        InstructionType instructionType = null;
    }

    public Instruction(IToken mnemonic) {
        this.mnemonic = mnemonic;
        IToken label = null;
        IToken offset = null;
        InstructionType instructionType = null;
    }

    public Instruction(IToken mnemonic, InstructionType instructionType) {
        this.mnemonic = mnemonic;
        IToken label = null;
        IToken offset = null;
        this.instructionType = instructionType;
    }

    public Instruction(IToken mnemonic, IToken label, IToken offset, InstructionType is){
        this.mnemonic = mnemonic;
        this.label = label;
        this.offset = offset;
        this.instructionType = is;
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