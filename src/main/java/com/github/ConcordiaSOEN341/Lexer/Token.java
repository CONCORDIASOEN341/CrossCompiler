package com.github.ConcordiaSOEN341.Lexer;

public class Token {

    private int line;
    private int column;

    public enum TokenType {
        MNEMONIC,
        LABEL,
        OFFSET,
        COMMENT
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
