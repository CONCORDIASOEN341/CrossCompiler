package com.github.ConcordiaSOEN341.Parser;

import com.github.ConcordiaSOEN341.Interfaces.IInstruction;
import com.github.ConcordiaSOEN341.Interfaces.IToken;

//For Sprint 2, instruction is only a mnemonic token
public class Instruction implements IInstruction {
    private IToken mnemonic;
    private Operand operand;
    private InstructionType instructionType;

    public Instruction() {
        this.mnemonic = null;
    }

    public Instruction(IToken mnemonic) {
        this.mnemonic = mnemonic;
    }

    public Instruction(IToken mnemonic, InstructionType instructionType) {
        this.mnemonic = mnemonic;
        this.instructionType = instructionType;
    }

    public Instruction(IToken mnemonic, Operand operand, InstructionType instructionType) {
        this.mnemonic = mnemonic;
        this.operand = operand;
        this.instructionType = instructionType;
    }

    public IToken getMnemonic() {
        return mnemonic;
    }

    public void setMnemonic(IToken mnemonic) {
        this.mnemonic = mnemonic;
    }

    public Operand getOperand(){
        return operand;
    }

    public void setOperand(Operand operand) {
        this.operand = operand;
    }

    public InstructionType getInstructionType(){
        return instructionType;
    }

    public void setInstructionType(InstructionType instructionType) {
        this.instructionType = instructionType;
    }

    public String toString(){
        return mnemonic.getTokenString();
    }

}