package com.github.ConcordiaSOEN341.Lexer;

public class Position implements IPosition {
    private int line;
    private int startColumn;
    private int endColumn;

    public Position() {
        this(0, 0, 0);
    }

    public Position(int line) {
        this(line, 0, 0);
    }

    public Position(int line, int startColumn, int endColumn) {
        this.line = line;
        this.startColumn = startColumn;
        this.endColumn = endColumn;
    }

    public String toString() {
        return line + " " + startColumn + " " + endColumn;
    }

    public int getLine() {
        return line;
    }

    public int getStartColumn() {
        return startColumn;
    }

    public int getEndColumn() {
        return endColumn;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public void setStartColumn(int startColumn) {
        this.startColumn = startColumn;
    }

    public void setEndColumn(int endColumn) {
        this.endColumn = endColumn;
    }
}
