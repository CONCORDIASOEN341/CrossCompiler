package com.github.ConcordiaSOEN341.Interfaces;

import com.github.ConcordiaSOEN341.Error.ErrorType;

public interface IError {
    ErrorType getErrorType();

    IPosition getPosition();
}
