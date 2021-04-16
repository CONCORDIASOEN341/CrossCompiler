package com.github.ConcordiaSOEN341.Error;

import com.github.ConcordiaSOEN341.Lexer.IPosition;

public class Error implements IError {
    private final ErrorType errorType;
    private final IPosition position;

    public Error(ErrorType errorType, IPosition position) {
        this.errorType = errorType;
        this.position = position;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public IPosition getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "Error:line " + position.getLine() + ": " + errorType;
    }
}
