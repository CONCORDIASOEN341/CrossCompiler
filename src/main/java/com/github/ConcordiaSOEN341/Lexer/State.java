package com.github.ConcordiaSOEN341.Lexer;

import com.github.ConcordiaSOEN341.Error.ErrorType;
import com.github.ConcordiaSOEN341.Interfaces.ILogger;
import com.github.ConcordiaSOEN341.Interfaces.IState;
import com.github.ConcordiaSOEN341.Logger.LoggerFactory;
import com.github.ConcordiaSOEN341.Logger.LoggerType;

public class State implements IState {

    private int stateID;
    private TokenType type;
    private ErrorType errorType;
    private boolean backtrack;


    public State(int id, TokenType t, boolean bt){
        stateID = id;
        type = t;
        errorType = ErrorType.getDefault();
        backtrack = bt;
    }

    public State(int id, TokenType t, boolean bt, ErrorType e){
        stateID = id;
        type = t;
        errorType = e;
        backtrack = bt;
    }

    public int getStateID() {
        return stateID;
    }

    public void setStateID(int stateID) {
        this.stateID = stateID;
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }

    public boolean isBacktrack() {
        return backtrack;
    }

    public void setBacktrack(boolean backtrack) {
        this.backtrack = backtrack;
    }



}
