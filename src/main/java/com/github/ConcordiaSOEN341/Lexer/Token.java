package com.github.ConcordiaSOEN341.Lexer;

public class Token {

    private int line;
    private int column;
    private String tokenString;
    private TokenType tokenType;

    public Token(String tokenString, int line, int column, TokenType tokenType) {
        this.tokenString = tokenString;
        this.line = line;
        this.column = column;
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

    public int getColumn() {
        return column;
    }

    public void setColumn(int startColumn) {
        this.column = startColumn;
    }
}
