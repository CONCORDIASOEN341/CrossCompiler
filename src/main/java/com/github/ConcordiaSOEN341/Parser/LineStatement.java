package com.github.ConcordiaSOEN341.Parser;

import com.github.ConcordiaSOEN341.Interfaces.IDirective;
import com.github.ConcordiaSOEN341.Interfaces.IInstruction;
import com.github.ConcordiaSOEN341.Interfaces.ILineStatement;
import com.github.ConcordiaSOEN341.Interfaces.IToken;
import com.github.ConcordiaSOEN341.Lexer.Token;

public class LineStatement implements ILineStatement {
    private IToken label;
    private IInstruction instruction;
    private IDirective directive;
    private IToken comment;
    private IToken eol;

    public LineStatement(IToken label, IInstruction instruction) {
        this.label = label;
        this.instruction = instruction;
    }

    public LineStatement(IInstruction instruction) {
        this.instruction = instruction;
    }

    public LineStatement() {
        this.label = new Token();
        this.instruction = new Instruction();
        this.directive = new Directive();
        this.comment = new Token();
        this.eol = new Token();
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
        return "[ " + label.getTokenString() + " ] [ "+ instruction + " | " + directive + " ] [ " + comment.getTokenString() + " ] " + eol.getTokenString() + " .\n";
    }

}