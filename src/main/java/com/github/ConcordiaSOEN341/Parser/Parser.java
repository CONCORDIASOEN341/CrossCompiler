package com.github.ConcordiaSOEN341.Parser;

import com.github.ConcordiaSOEN341.Lexer.Lexer;
import com.github.ConcordiaSOEN341.Lexer.Token;
import com.github.ConcordiaSOEN341.Lexer.TokenType;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private List<Token> tokenList;
    private ArrayList<LineStatement> IR;

    public Parser() {
        this.tokenList = new ArrayList<>();
        this.IR = new ArrayList<>();
    }

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

    /* Create the IR here
    1.    Get all tokens per line, place each token into temp list of what is in the line
    2.    Check this temp list for validity (For now valid means just mnemonic+EOL or empty line)
    3.    do this as long as the line has not reached EOF and fill the IR if valid
    */
    public ArrayList generateIR(String file) {
        Lexer lexer = new Lexer(file);
        Token tempToken = null;
        ArrayList<Token> tList = new ArrayList<Token>();
        do {
            tempToken = lexer.getNextToken();
            tList.add(tempToken);
            if (tempToken.getTokenType() == TokenType.EOL) {
                if (isValidLine(tList)) {
                    addToIR(tList);
                } else {
                    break;
                }
                tList.clear();  //reset the temp list for the next line
            }
        } while (tempToken.getTokenType() != TokenType.EOF);

        return IR;
    }

    // For now(Sprint 2) just checks that the first element is a mnemonic followed by EOL, or just an empty line
    // LineStatement = [ Label ] [ Instruction | Directive ] [ Comment ] EOL
    private boolean isValidLine(ArrayList<Token> line) {
        if ((line.get(0).getTokenType() == TokenType.MNEMONIC) && (line.get(1).getTokenType() == TokenType.EOL)) {
            return true;
        } else if (line.get(0).getTokenType() == TokenType.EOL){
            System.out.println("Empty line on line "+line.get(0).getLine());
            return true;
        } else {
            System.out.println("ERROR: LINE "+line.get(0).getLine());
            return false;
        }
    }

    // Add IR line here if valid, creates line statement from list of Tokens then adds to list
    private void addToIR(ArrayList<Token> line) {
        Instruction instruct = new Instruction(line.get(0));
        Token tempEOL = new Token(0,0,0);
        tempEOL.setTokenType(TokenType.EOL);
        LineStatement lStatement = new LineStatement(instruct, tempEOL);

        IR.add(lStatement);
    }

    //(might not need this)
    public Instruction parseInstruction(LineStatement lineStatement) {
        Instruction instructionToReturn;
        instructionToReturn = lineStatement.getInstruction();

        return instructionToReturn;
    }


}
