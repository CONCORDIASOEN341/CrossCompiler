package com.github.ConcordiaSOEN341.Parser;

import com.github.ConcordiaSOEN341.Lexer.Token;

public class LineStatement {
    //only an instruction & EOL for sprint 2
    private Instruction instruction;
    private Token eol;

    public LineStatement() {
        this.instruction = null;
        this.eol = null;
    }

    public LineStatement(Instruction instruction) {
        this.instruction = instruction;
        this.eol = null;
    }

    public LineStatement(Token eol) {
        this.instruction = null;
        this.eol = eol;
    }

    public LineStatement(Instruction instruction, Token eol) {
        this.instruction = instruction;
        this.eol = eol;
    }

    public Instruction getInstruction() {
        return instruction;
    }

    public void setInstruction(Instruction instruction) {
        this.instruction = instruction;
    }

    public Token getEOL() {
        return eol;
    }

    public void setEOL(Token EOL) {
        this.eol = EOL;
    }

    public String toString(){
        return "["+ instruction.toString() + "][" + eol.getTokenType()+"]\n";
    }

}