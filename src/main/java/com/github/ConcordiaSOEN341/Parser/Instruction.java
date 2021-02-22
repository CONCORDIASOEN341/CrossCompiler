package com.github.ConcordiaSOEN341.Parser;

import com.github.ConcordiaSOEN341.Lexer.Token;

public class Instruction extends LineStatement{
    private Token mnemonic;
    private Token operand;

    public Instruction(Token mnemonic) {
        this.mnemonic = mnemonic;
        this.operand = null;
    }

    public Instruction(Token mnemonic, Token operand) {
        this.mnemonic = mnemonic;
        this.operand = operand;
    }

    public Token getMnemonic() {
        return mnemonic;
    }

    public void setMnemonic(Token mnemonic) {
        this.mnemonic = mnemonic;
    }

    public Token getOperand() {
        return operand;
    }

    public void setOperand() {
        this.operand = operand;
    }

    public String toString(){
        return mnemonic.getTokenString();
    }

}