package com.github.ConcordiaSOEN341.Parser;

import com.github.ConcordiaSOEN341.Interfaces.IInstruction;
import com.github.ConcordiaSOEN341.Interfaces.ILineStatement;
import com.github.ConcordiaSOEN341.Interfaces.IToken;

public class LineStatement implements ILineStatement {
    //only an instruction & EOL for sprint 2
    private IInstruction instruction;
    private IToken directive;
    private IToken comment;
    private IToken eol;

    public LineStatement() {
        this.instruction = null;
        this.directive = null;
        this.comment = null;
        this.eol = null;
    }

    public LineStatement(IInstruction instruction) {
        this.instruction = instruction;
        this.directive = null;
        this.comment = null;
        this.eol = null;
    }

    public LineStatement(IToken eol) {
        this.instruction = null;
        this.directive = null;
        this.comment = null;
        this.eol = eol;
    }

    public LineStatement(IInstruction instruction, IToken eol) {
        this.instruction = instruction;
        this.directive = null;
        this.comment = null;
        this.eol = eol;
    }

    public LineStatement(IInstruction instruction, IToken eol, IToken comment) {
        this.instruction = instruction;
        this.directive = null;
        this.comment = comment;
        this.eol = eol;
    }

    public LineStatement(IInstruction instruction, IToken directive, IToken comment, IToken eol) {
        this.instruction = instruction;
        this.directive = directive;
        this.comment = comment;
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

    public IToken getDirective() {
        return directive;
    }

    public void setDirective(IToken directive) {
        this.directive = directive;
    }

    public IToken getComment() {
        return comment;
    }

    public void setComment(IToken comment) {
        this.comment = comment;
    }

    public String toString() {
        return "[" + instruction.toString() + "][" + eol.getTokenType() + "]\n";
    }

}