package com.github.ConcordiaSOEN341.Lexer;

import com.github.ConcordiaSOEN341.CodeGenMaps.CodeMap;
import com.github.ConcordiaSOEN341.CodeGenMaps.StateMap;
import com.github.ConcordiaSOEN341.Reader.Reader;

public class Lexer {
    private int currentLine = 1;
    private int currentCol = 0;
    private final Reader reader;
    private final StateMap sm;
    private final CodeMap cg;
    private int stateID = 0;
    private int temp = 0;

    public Lexer(String filename){
        reader = new Reader(filename);
        sm = new StateMap();
        cg = new CodeMap();
    }

    // Bad but working attempt at DFA
    private TokenType getState(char character){

        if(stateID == 1 && !Character.isWhitespace(character)){
            stateID = 2;
        } else if(stateID == 2 && Character.isWhitespace(character)) {
            stateID = 3;
        }

        return sm.getValue(stateID);

    }



    public Token getNextToken(){

        Token token = new Token(currentLine, currentCol, currentCol);
        StringBuilder tokenString = new StringBuilder();
        TokenType type;


        // if we encountered an EOL EOF right after letters
        if (temp == reader.getEof()){
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
        type = TokenType.START;
        boolean tokenStarted = false;

        // loop till we have read a token
        while(type == TokenType.START) {
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
                    type = TokenType.EOL;
                    token.setTokenType(type);
                } else {
                    temp = 10;
                    type = TokenType.IDENTIFIER;
                }
                currentCol = 0;
                currentLine++;
                continue;
            } else {
                currentCol++;
            }

            if (currentChar == reader.getEof()) {
                if(stateID == 1){
                    type = TokenType.EOF;
                    token.setTokenType(type);
                } else {
                    temp = reader.getEof();
                    type = TokenType.IDENTIFIER;
                }
                continue;
            }

            type = getState((char) currentChar);
            tokenString.append((char) currentChar);
        }

        // trim and finalize token
        token.setTokenString(tokenString.toString().trim());
        token.setEndColumn(token.getStartColumn()+token.getTokenString().length());

        if(type == TokenType.IDENTIFIER){
            if(cg.getValue(token.getTokenString()) != null){
                type = TokenType.MNEMONIC;
            }
            token.setTokenType(type);
        }

        return token;

    }

}
