package com.github.ConcordiaSOEN341.Parser;

import com.github.ConcordiaSOEN341.Lexer.Token;

public class LineStatement {
    //only an instruction & EOL for sprint 2
    private Instruction instruction;
    private Token EOL;

    public LineStatement() {
        this.instruction = instruction;
        this.EOL = EOL;
    }

    public LineStatement(Instruction instruction) {
        this.instruction = instruction;
        this.EOL = EOL;
    }

    public LineStatement(Instruction instruction, Token EOL) {
        this.instruction = instruction;
        this.EOL = EOL;
    }

    public Instruction getInstruction() {
        return instruction;
    }

    public void setInstruction(Instruction instruction) {
        this.instruction = instruction;
    }

    public Token getEOL() {
        return EOL;
    }

    public void setEOL(Token EOL) {
        this.EOL = EOL;
    }

    public String toString(){
        return "["+ instruction.toString() + "][" + EOL.getTokenType()+"]\n";
    }

}