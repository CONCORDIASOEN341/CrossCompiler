package com.github.ConcordiaSOEN341.Lexer;

import com.github.ConcordiaSOEN341.Interfaces.ILogger;
import com.github.ConcordiaSOEN341.Interfaces.IPosition;
import com.github.ConcordiaSOEN341.Interfaces.IToken;
import com.github.ConcordiaSOEN341.Logger.LoggerFactory;
import com.github.ConcordiaSOEN341.Logger.LoggerType;

public class Token implements IToken {
    private IPosition position;
    private String tokenString;
    private TokenType tokenType;

    private final ILogger logger = LoggerFactory.getLogger(LoggerType.LEXER);

    public Token(){
        this.tokenString = "";
        this.position = new Position();
        this.tokenType = TokenType.ERROR;
        logger.log("Token created " + this);
    }

    public Token(IPosition position) {
        this.tokenString = "";
        this.position = position;
        this.tokenType = TokenType.ERROR;
        logger.log("Token created " + this);
    }

    public Token(String tokenString) {
        this.tokenString = tokenString;
        logger.log("Token created " + this);
    }

    public Token(String tokenString, IPosition position, TokenType tokenType) {
        this.tokenString = tokenString;
        this.position = position;
        this.tokenType = tokenType;
        logger.log("Token created " + this);
    }

    public String toString() {
        return tokenString + " " + position + " " + tokenType;
    }

    public IPosition getPosition() {
        return position;
    }

    public void setPosition(IPosition pos) {
        position = pos;
    }

    public String getTokenString() {
        return tokenString;
    }

    public void setTokenString(String tokenString) {
        this.tokenString = tokenString;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }
}
