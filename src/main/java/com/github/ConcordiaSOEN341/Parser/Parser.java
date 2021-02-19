package com.github.ConcordiaSOEN341.Parser;

import com.github.ConcordiaSOEN341.Lexer.Token;

import java.util.List;

public class Parser {
    private List<Token> tokenList;
    private List<LineStatement> IR;

    public Parser(List<Token> tokenList, List<LineStatement> IR) {
        this.tokenList = tokenList; //IR??
        this.IR = IR;               //Also IR??
    }

    public AddToIR(LineStatement lineStatement) {

        IR.add(lineStatement);

    }

    //for the sprint 2 the lineStatement only consists of an instruction, so this isnt't doing anything yet
    public Token parseInstruction(LineStatement lineStatement) {

    }





}
