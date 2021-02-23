package com.github.ConcordiaSOEN341.Parser;

import com.github.ConcordiaSOEN341.Lexer.Token;

//For Sprint 2, instruction is only a mnemonic token
public class Instruction {
    private Token mnemonic;

    public Instruction() {
        this.mnemonic = null;
    }

    public Instruction(Token mnemonic) {
        this.mnemonic = mnemonic;
    }

    public Token getMnemonic() {
        return mnemonic;
    }

    public void setMnemonic(Token mnemonic) {
        this.mnemonic = mnemonic;
    }

    public String toString(){
        return mnemonic.getTokenString();
    }

}