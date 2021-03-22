package com.github.ConcordiaSOEN341.Lexer;

import com.github.ConcordiaSOEN341.Error.LexerErrorType;
import com.github.ConcordiaSOEN341.Interfaces.IState;

public class State implements IState {

    private int stateID;
    private TokenType type;
//    private LexerErrorType errorType;
    private boolean backtrack;

    public State(int id, TokenType t, boolean bt){
        stateID = id;
        type = t;
//        errorType = e;
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

//    public LexerErrorType getErrorType() {
//        return errorType;
//    }
//
//    public void setErrorType(LexerErrorType errorType) {
//        this.errorType = errorType;
//    }

    public boolean isBacktrack() {
        return backtrack;
    }

    public void setBacktrack(boolean backtrack) {
        this.backtrack = backtrack;
    }



}
