package com.github.ConcordiaSOEN341.Lexer;

import com.github.ConcordiaSOEN341.Interfaces.ILexer;
import com.github.ConcordiaSOEN341.Interfaces.IReader;
import com.github.ConcordiaSOEN341.Interfaces.IToken;
import com.github.ConcordiaSOEN341.Maps.CodeMap;
import com.github.ConcordiaSOEN341.Maps.StateMap;

import java.util.ArrayList;

public class Lexer implements ILexer {
    private int currentLine = 1;
    private int currentCol = 0;
    private final IReader reader;
    private final StateMap sm;
    private final CodeMap cm;
    private int stateID = 0;
    private int temp = 0;

    public Lexer(IReader r) {
        reader = r;
        sm = new StateMap();
        cm = new CodeMap();
    }

    public ArrayList<IToken> generateTokenList() {
        ArrayList<IToken> tokenList = new ArrayList<>();
        IToken t;

        do {
            t = getNextToken();
            tokenList.add(t);

        } while (t.getTokenType() != TokenType.EOF);

        return tokenList;
    }

    // Bad but working attempt at DFA
    private TokenType getState(int character) {

        if (stateID == 1 && character == reader.getEof()) {
            stateID = 7;
        } else if (stateID == 1 && character == '\n') {
            stateID = 6;
        } else if (stateID == 1 && Character.isLetter(character)) {
            stateID = 2;
        } else if (stateID == 2 && (Character.isWhitespace(character) || isBackTrack(character))) {
            stateID = 3;
        } else if (stateID == 1 && character == ';') {
            stateID = 4;
        } else if (stateID == 4 && hasNoChar(character)) {
            stateID = 5;
        }
        return sm.getValue(stateID);

    }


    private IToken getNextToken() {
        // THIS HAS TO CHANGE SOMETIME!!!!
        if (temp == '\n') currentLine--;

        Token token = new Token(new Position(currentLine, currentCol, currentCol));
        StringBuilder tokenString = new StringBuilder();
        TokenType type;
        int startCol = 0;
        int line = 0;

        stateID = 1;
        type = TokenType.START;
        boolean tokenStarted = false;

        // loop till we have read a token
        while (type == TokenType.START) {
            int currentChar;
            currentChar = (temp == 0) ? reader.read() : temp;

            // Gather token info at start
            if (!tokenStarted && currentChar != ' ' && currentChar != '\r') {
                startCol = currentCol;
                line = currentLine;
                tokenStarted = true;
            }

            if (currentChar == '\n') {
                currentCol = 0;
                currentLine++;
            }

            if (isBackTrack(currentChar)) {
                if (stateID != 1) {
                    type = getState(currentChar);
                    temp = currentChar;
                    continue;
                } else {
                    temp = 0;
                }
            }

            type = getState(currentChar);

            if (!hasNoChar(currentChar)) {
                currentCol++;
                tokenString.append((char) currentChar);
            }
        }

        // trim and finalize token

        token.setTokenString(tokenString.toString().trim());
        token.setPosition(new Position(line, startCol, startCol + token.getTokenString().length()));

        if (type == TokenType.IDENTIFIER) {
            if (cm.getValue(token.getTokenString()) != null) {
                type = TokenType.MNEMONIC;
            }
        }

        token.setTokenType(type);

        return token;

    }

    private boolean isBackTrack(int character) {
        return character == reader.getEof() || character == '\n' || character == ';';
    }

    private boolean hasNoChar(int character) {
        return character == reader.getEof() || character == '\n';
    }

}
