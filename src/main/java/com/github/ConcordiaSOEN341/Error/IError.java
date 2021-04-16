package com.github.ConcordiaSOEN341.Error;

import com.github.ConcordiaSOEN341.Lexer.IPosition;

public interface IError {
    ErrorType getErrorType();

    IPosition getPosition();
}
