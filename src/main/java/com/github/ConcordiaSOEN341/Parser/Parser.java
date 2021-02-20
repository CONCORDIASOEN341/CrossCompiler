package com.github.ConcordiaSOEN341.Parser;

import com.github.ConcordiaSOEN341.Lexer.Token;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private List<Token> tokenList;
    private List<LineStatement> IR;

    public Parser(List<Token> tokenList) {
        this.tokenList = tokenList;
        this.IR = new ArrayList<>();
    }

    public List<Token> getTokenList() {
        return tokenList;
    }

    public void setTokenList(List<Token> tokenList) {
        this.tokenList = tokenList;
    }

    private void addToIR(LineStatement lineStatement) {
        IR.add(lineStatement);
    }

    //for the sprint 2 the lineStatement only consists of an instruction, so this isnt't doing anything yet
    public Token parseInstruction(LineStatement lineStatement) {
        return null;
    }


}
