package com.github.ConcordiaSOEN341.Lexer;

import com.github.ConcordiaSOEN341.Interfaces.ILogger;
import com.github.ConcordiaSOEN341.Interfaces.IPosition;
import com.github.ConcordiaSOEN341.Logger.LoggerFactory;
import com.github.ConcordiaSOEN341.Logger.LoggerType;

public class Position implements IPosition {
    private int line;
    private int startColumn;
    private int endColumn;

    private final ILogger logger = LoggerFactory.getLogger(LoggerType.LEXER);

    public Position(){
        this.line = 0;
        this.startColumn = 0;
        this.endColumn = 0;
        logger.log("Position created: " + this);
    }

    public Position(int line, int startColumn, int endColumn) {
        this.line = line;
        this.startColumn = startColumn;
        this.endColumn = endColumn;
        logger.log("Position created: " + this);
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
