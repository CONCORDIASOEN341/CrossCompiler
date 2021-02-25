package com.github.ConcordiaSOEN341.Parser;

import com.github.ConcordiaSOEN341.Interfaces.IInstruction;
import com.github.ConcordiaSOEN341.Interfaces.IToken;

//For Sprint 2, instruction is only a mnemonic token
public class Instruction implements IInstruction {
    private IToken mnemonic;

    public Instruction() {
        this.mnemonic = null;
    }

    public Instruction(IToken mnemonic) {
        this.mnemonic = mnemonic;
    }

    public IToken getMnemonic() {
        return mnemonic;
    }

    public void setMnemonic(IToken mnemonic) {
        this.mnemonic = mnemonic;
    }

    public String toString(){
        return mnemonic.getTokenString();
    }

}