package com.github.ConcordiaSOEN341.Parser;

import com.github.ConcordiaSOEN341.Interfaces.IInstruction;
import com.github.ConcordiaSOEN341.Interfaces.ILineStatement;
import com.github.ConcordiaSOEN341.Interfaces.IToken;

public class LineStatement implements ILineStatement {
    //only an instruction & EOL for sprint 2
    private IInstruction instruction;
    private IToken eol;

    public LineStatement() {
        this.instruction = null;
        this.eol = null;
    }

    public LineStatement(IInstruction instruction) {
        this.instruction = instruction;
        this.eol = null;
    }

    public LineStatement(IToken eol) {
        this.instruction = null;
        this.eol = eol;
    }

    public LineStatement(IInstruction instruction, IToken eol) {
        this.instruction = instruction;
        this.eol = eol;
    }

    public IInstruction getInstruction() {
        return instruction;
    }

    public void setInstruction(IInstruction instruction) {
        this.instruction = instruction;
    }

    public IToken getEOL() {
        return eol;
    }

    public void setEOL(IToken EOL) {
        this.eol = EOL;
    }

    public String toString(){
        return "["+ instruction.toString() + "][" + eol.getTokenType()+"]\n";
    }

}