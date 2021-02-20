package com.github.ConcordiaSOEN341.Lexer;

public class Token {

    private int line;
    private int startColumn;
    private int endColumn;
    private String tokenString;
    private TokenType tokenType;

    public Token(String tokenString, int line, int startColumn, int endColumn, TokenType tokenType) {
        this.tokenString = tokenString;
        this.line = line;
        this.startColumn = startColumn;
        this.endColumn = endColumn;
        this.tokenType = tokenType;
    }

    public enum TokenType {
        MNEMONIC,
        LABEL,
        OFFSET,
        CSTRING,
        COMMENT,
        EOF,
        EOL
    }


    public int getLine() {
        return line;
    }

    public void setLine(int startLine) {
        this.line = startLine;
    }

    public int getStartColumn() {
        return startColumn;
    }

    public void setColumn(int startColumn) {
        this.startColumn = startColumn;
    }

    public int getEndColumn() {
        return endColumn;
    }

    public void setEndColumn(int endColumn) {
        this.endColumn = endColumn;
    }
}
