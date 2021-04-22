package com.github.ConcordiaSOEN341.Error;

import com.github.ConcordiaSOEN341.Lexer.IPosition;

public class Error implements IError {
    private final String data;
    private final ErrorType errorType;
    private final IPosition position;

    public Error(ErrorType errorType, IPosition position) {
        this("", errorType, position);
    }

    public Error(String data, ErrorType errorType, IPosition position) {
        this.data = data;
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
        return "Error at line " + position.getLine() + ": " + ((data.length() == 0)? "" : data + " ") +  errorType;
    }
}
