package com.github.ConcordiaSOEN341.Parser;

import com.github.ConcordiaSOEN341.Lexer.IToken;
import com.github.ConcordiaSOEN341.Lexer.Token;

public class Directive implements IDirective {
    private final IToken directive;
    private IToken cstring;

    public Directive() {
        directive = new Token();
        cstring = new Token();
    }

    public Directive(IToken d) {
        directive = d;
        cstring = new Token();
    }

    public Directive(IToken d, IToken cS) {
        directive = d;
        cstring = cS;
    }

    @Override
    public IToken getDir() {
        return directive;
    }

    @Override
    public IToken getCString() {
        return cstring;
    }

    @Override
    public void setCString(IToken cs) {
        cstring = cs;
    }

    public String toString() {
        return directive.getTokenString() + " " + cstring.getTokenString();
    }


}
