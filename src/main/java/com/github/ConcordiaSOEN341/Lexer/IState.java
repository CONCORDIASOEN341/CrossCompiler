package com.github.ConcordiaSOEN341.Lexer;

import com.github.ConcordiaSOEN341.Error.ErrorType;

public interface IState {
    int getStateID();

    void setStateID(int stateID);

    TokenType getType();

    void setType(TokenType type);

    ErrorType getErrorType();

    void setErrorType(ErrorType errorType);

    boolean isBacktrack();

    void setBacktrack(boolean backtrack);
}
