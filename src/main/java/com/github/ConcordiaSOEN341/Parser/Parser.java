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

    //for the sprint 2 the lineStatement only consists of an instruction, so this isn't doing anything yet
    // TODO: Implement method -- Should be done now, change if needed
    public Instruction parseInstruction(LineStatement lineStatement) {
        Instruction instructionToReturn;
        instructionToReturn = lineStatement.getInstruction();

        return instructionToReturn;
    }


}
