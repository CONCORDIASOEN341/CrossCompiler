package com.github.ConcordiaSOEN341.Lexer;

import com.github.ConcordiaSOEN341.Interfaces.IToken;

public class Token implements IToken {
    private Position position;
    private String tokenString;
    private TokenType tokenType;

    public Token(Position position) {
        this.tokenString = "";
        this.position = position;
        this.tokenType = TokenType.ERROR;
    }

    public Token(String tokenString, Position position, TokenType tokenType) {
        this.tokenString = tokenString;
        this.position = position;
        this.tokenType = tokenType;
    }

    public String toString() {
        return tokenString + " " + position.getLine() + " " + position.getStartColumn() + " " + position.getEndColumn() + " " + tokenType;
    }

    public int getLine() {
        return position.getLine();
    }

    public void setLine(int line) {
        this.position.setLine(line);
    }

    public int getStartColumn() {
        return position.getStartColumn();
    }

    public void setStartColumn(int startColumn) {
        this.position.setStartColumn(startColumn);
    }

    public int getEndColumn() {
        return position.getEndColumn();
    }

    public void setEndColumn(int endColumn) {
        this.position.setEndColumn(endColumn);
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
