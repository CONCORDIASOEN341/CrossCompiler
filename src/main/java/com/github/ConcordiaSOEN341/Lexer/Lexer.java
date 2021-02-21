package com.github.ConcordiaSOEN341.Lexer;

import com.github.ConcordiaSOEN341.CodeGenMaps.CodeMap;
import com.github.ConcordiaSOEN341.CodeGenMaps.StateMap;
import com.github.ConcordiaSOEN341.Reader.Reader;

import java.io.File;

public class Lexer {
    private int currentLine = 1;
    private int currentCol = 0;
    private final Reader reader;
    private final StateMap sm;
    private final CodeMap cg;
    private int stateID = 0;
    private int temp = 0;

    public Lexer(String filename){
        reader = new Reader(new File(filename));
        sm = new StateMap();
        cg = new CodeMap();
    }

    // Bad but working attempt at DFA
    private String getState(int character){

        if(stateID == 1 && !(character == ' ' || character == '\r' || character == '\n')){
            stateID = 2;
        } else if(stateID == 2 && (character == ' ' || character == '\r' || character == '\n')) {
            stateID = 3;
        }

        return sm.getValue(stateID);

    }

    public Token getNextToken(){

        Token token = new Token(currentLine, currentCol, currentCol);
        StringBuilder tokenString = new StringBuilder();
        String stateName;


        // if we encountered an EOL EOF right after letters
        if (temp == -1){
            token.setTokenType(TokenType.EOF);
            temp = 0;
            return token;
        }

        // colomns are not where it actually is but we know its at the end of the line it is on
        if (temp == 10){
            token.setTokenType(TokenType.EOL);
            token.setLine(currentLine-1);
            temp = 0;
            return token;
        }

        stateID = 1;
        stateName = "Start";
        boolean tokenStarted = false;

        while(stateName.equals("Start")) {
            int currentChar;
            currentChar = reader.read();

            // Gather token info at start
            if(!tokenStarted && currentChar != ' ' && currentChar != '\r'){
                token.setStartColumn(currentCol);
                token.setLine(currentLine);
                tokenStarted = true;
            }

            if (currentChar == '\n') {
                if(stateID == 1){
                    token.setTokenType(TokenType.EOL);
                    stateName = "EOL";
                } else {
                    temp = 10;
                    stateName = "Identifier";
                }
                currentCol = 0;
                currentLine++;
                continue;
            } else {
                currentCol++;
            }

            if (currentChar == -1) {
                if(stateID == 1){
                    token.setTokenType(TokenType.EOF);
                    stateName = "EOF";
                } else {
                    temp = -1;
                    stateName = "Identifier";
                }
                continue;
            }

            stateName = getState(currentChar);
            tokenString.append((char) currentChar);
        }

        // trim and finalize token
        token.setTokenString(tokenString.toString().trim());
        token.setEndColumn(token.getStartColumn()+token.getTokenString().length());

        if(stateName.equals("Identifier")){
            TokenType type = TokenType.IDENTIFIER;
            if(cg.getValue(token.getTokenString()) != null){
                type = TokenType.MNEMONIC;
            }
            token.setTokenType(type);
        }

        return token;

    }

}
