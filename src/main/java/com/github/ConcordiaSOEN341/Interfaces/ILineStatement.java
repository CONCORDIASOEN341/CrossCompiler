package com.github.ConcordiaSOEN341.Interfaces;

public interface ILineStatement {

    IToken getLabel();

    IInstruction getInstruction();

    IToken getEOL();

    IDirective getDirective();

    IToken getComment();

    void setLabel(IToken label);

    void setInstruction(IInstruction instruction);

    void setEOL(IToken EOL);

    void setDirective(IDirective directive);

    void setComment(IToken comment);
}
