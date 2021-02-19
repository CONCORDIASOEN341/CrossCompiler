package com.github.ConcordiaSOEN341.Parser;

public class Instruction {
    private Token mnemonic = null;
    private Token operand = null;

    public Instruction(Token mnemonic, Token operand) {
        this.mnemonic = mnemonic;
        this.operand = operand;
    }

}