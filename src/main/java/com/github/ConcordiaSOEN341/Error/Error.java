package com.github.ConcordiaSOEN341.Error;

import com.github.ConcordiaSOEN341.Interfaces.ILineStatement;

public class Error {
    private ErrorType errorType;
    private ILineStatement invalidLine;

    public Error(ErrorType errorType, ILineStatement invalidLine) {
        this.errorType = errorType;
        this.invalidLine = invalidLine;
    }

    public ILineStatement getInvalidLine() {
        return invalidLine;
    }

    // TODO: Change invalidLine.toString()
    @Override
    public String toString() {
        return errorType.toString() + ": " + invalidLine.toString();
    }
}
