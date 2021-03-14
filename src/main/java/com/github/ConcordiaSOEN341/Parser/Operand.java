package com.github.ConcordiaSOEN341.Parser;
import com.github.ConcordiaSOEN341.Interfaces.IToken;

public class Operand {
    private IToken label;
    private IToken offset;

    private InstructionType instructionType;

    public Operand() {
        this.label = null;
    }

    public Operand(IToken label) {
        this.label = label;
    }

    public Operand(IToken label, IToken offset) {
        this.label = label;
        this.offset = offset;
    }

    public IToken getLabel() {
        return label;
    }

    public void setLabel(IToken label) {
        this.label = label;
    }

    public IToken getOffset() {
        return offset;
    }

    public void setOffset(IToken offset){
        this.offset = offset;
    }

}
