package com.github.ConcordiaSOEN341.Parser;

import com.github.ConcordiaSOEN341.Interfaces.IDirective;
import com.github.ConcordiaSOEN341.Interfaces.IInstruction;
import com.github.ConcordiaSOEN341.Interfaces.ILineStatement;
import com.github.ConcordiaSOEN341.Interfaces.IToken;

public class LineStatement implements ILineStatement {
    private IToken label;
    private IInstruction instruction;
    private IDirective directive;
    private IToken comment;
    private IToken eol;

    public LineStatement(IInstruction instruction) {
        this.instruction = instruction;
    }

    public LineStatement() {
        this.instruction = null;
        this.directive = null;
        this.comment = null;
        this.eol = null;
    }

    public IToken getLabel() {
        return label;
    }

    public void setLabel(IToken label) {
        this.label = label;
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

    public IDirective getDirective() {
        return directive;
    }

    public void setDirective(IDirective directive) {
        this.directive = directive;
    }

    public IToken getComment() {
        return comment;
    }

    public void setComment(IToken comment) {
        this.comment = comment;
    }

    public String toString() {
        return "[ " + label.getTokenString() + " ] [ "+ instruction.toString() + " | " + directive.toString() + " ] [ " + comment.getTokenString() + " ] " + eol.getTokenType() + " .\n";
    }

}