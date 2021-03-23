package com.github.ConcordiaSOEN341.Error;

import com.github.ConcordiaSOEN341.Interfaces.IError;
import com.github.ConcordiaSOEN341.Interfaces.IPosition;

public class Error implements IError {
    private ErrorType errorType;
    private IPosition position;

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

    // TODO: Change invalidLine.toString() to smtg that rebuilds the initial line of code
    @Override
    public String toString() {
        return "Error:line " + position.getLine() + ": " + errorType;
    }
}
