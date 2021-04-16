package com.github.ConcordiaSOEN341.Lexer;

public interface IPosition {
    int getLine();

    int getStartColumn();

    int getEndColumn();

    void setLine(int line);

    void setStartColumn(int startColumn);

    void setEndColumn(int endColumn);
}
