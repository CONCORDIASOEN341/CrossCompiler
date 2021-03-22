package com.github.ConcordiaSOEN341.Error;

import com.github.ConcordiaSOEN341.Interfaces.IError;
import com.github.ConcordiaSOEN341.Interfaces.ILineStatement;

public class Error implements IError {
    private ErrorType errorType;
    private ILineStatement invalidLine;

    public Error(ErrorType errorType, ILineStatement invalidLine) {
        this.errorType = errorType;
        this.invalidLine = invalidLine;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public ILineStatement getInvalidLine() {
        return invalidLine;
    }

    // TODO: Change invalidLine.toString() to smtg that rebuilds the initial line of code
    @Override
    public String toString() {
        return invalidLine + "\n" + errorType;
    }
}
