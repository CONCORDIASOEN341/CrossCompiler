package com.github.ConcordiaSOEN341.Lexer;

import com.github.ConcordiaSOEN341.Interfaces.IToken;

public class Token implements IToken {

    private int line;
    private int startColumn;
    private int endColumn;
    private String tokenString;
    private TokenType tokenType;

    public Token(int line, int startColumn, int endColumn) {
        this.tokenString = "";
        this.line = line;
        this.startColumn = startColumn;
        this.endColumn = endColumn;
        this.tokenType = TokenType.ERROR;
    }

    public Token(String tokenString, int line, int startColumn, int endColumn, TokenType tokenType) {
        this.tokenString = tokenString;
        this.line = line;
        this.startColumn = startColumn;
        this.endColumn = endColumn;
        this.tokenType = tokenType;
    }

    public String toString(){
        return tokenString + " " + line + " " + startColumn + " " + endColumn+ " " + tokenType;
    }

    // TODO: Remove getters/setters that are not needed or not logical to have ?
    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getStartColumn() {
        return startColumn;
    }

    public void setStartColumn(int startColumn) {
        this.startColumn = startColumn;
    }

    public int getEndColumn() {
        return endColumn;
    }

    public void setEndColumn(int endColumn) {
        this.endColumn = endColumn;
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
