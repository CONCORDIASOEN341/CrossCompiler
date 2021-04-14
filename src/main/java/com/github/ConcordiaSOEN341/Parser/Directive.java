package com.github.ConcordiaSOEN341.Parser;

import com.github.ConcordiaSOEN341.Interfaces.IDirective;
import com.github.ConcordiaSOEN341.Interfaces.ILogger;
import com.github.ConcordiaSOEN341.Interfaces.IToken;
import com.github.ConcordiaSOEN341.Lexer.Token;
import com.github.ConcordiaSOEN341.Logger.LoggerFactory;
import com.github.ConcordiaSOEN341.Logger.LoggerType;

public class Directive implements IDirective {
    private IToken directive;
    private IToken cstring;

    private final ILogger logger = LoggerFactory.getLogger(LoggerType.PARSER);

    public Directive(){
        directive = new Token();
        cstring = new Token();
        logger.log("Directive created: " + this);
    }

    public Directive(IToken d){
        directive = d;
        cstring = new Token();
        logger.log("Directive created: " + this);
    }

    public Directive(IToken d, IToken cS){
        directive = d;
        cstring = cS;
        logger.log("Directive created: " + this);
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

    public String toString(){
        return directive.getTokenString() + " " + cstring.getTokenString();
    }


}
