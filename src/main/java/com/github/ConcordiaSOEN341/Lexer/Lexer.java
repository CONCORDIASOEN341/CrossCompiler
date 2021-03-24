package com.github.ConcordiaSOEN341.Lexer;

import com.github.ConcordiaSOEN341.Error.Error;
import com.github.ConcordiaSOEN341.Error.ErrorReporter;
import com.github.ConcordiaSOEN341.Interfaces.ILexer;
import com.github.ConcordiaSOEN341.Interfaces.IReader;
import com.github.ConcordiaSOEN341.Interfaces.IToken;
import com.github.ConcordiaSOEN341.Maps.CodeMap;

import java.util.ArrayList;

public class Lexer implements ILexer {
    private int currentLine = 1;
    private int currentCol = 0;
    private final IReader reader;
    private final CodeMap cm;
    private final DFA dfa;
    private int stateID = 0;
    private int temp = 0;

    public Lexer(IReader r) {
        reader = r;
        cm = new CodeMap();
        dfa = new DFA(r);
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

    private IToken getNextToken() {
        Token token = new Token(new Position(currentLine, currentCol, currentCol));
        StringBuilder tokenString = new StringBuilder();
        TokenType type;
        int startCol = 0;
        int line = 0;

        stateID = dfa.getInitialStateID();
        type = TokenType.START;
        boolean tokenStarted = false;
        int currentChar;
        int previousCol;
        int previousLine;
        int previousStateID;

        // loop till we have read a token
        while (type == TokenType.START) {
            if (temp == 0) {
                currentChar = reader.read();
            } else {
                currentChar = temp;
                temp = 0;
            }

            if (currentChar == '\r')
                continue;

            // Record token info at start
            if (!tokenStarted && currentChar != ' ' && currentChar != '\t') {
                startCol = currentCol;
                line = currentLine;
                tokenStarted = true;
            }

            previousCol = currentCol;
            previousLine = currentLine;

            if (currentChar == '\n') {
                currentCol = 0;
                currentLine++;
            } else {
                if (currentChar == '\t')
                    currentCol += 8;
                else
                    currentCol++;
            }

            previousStateID = stateID;
            stateID = dfa.getNextStateID(stateID, currentChar);
            type = dfa.getStateType(stateID);

            // TRACK ERRORS
            if (type == TokenType.ERROR) {
                stateID = (stateID == 0) ? previousStateID : stateID;
                ErrorReporter.record(new Error(dfa.getErrorType(stateID), new Position(previousLine, previousCol, previousCol + 1)));
                type = dfa.getStateType(stateID);
            }

            if (dfa.isBackTrack(stateID)) {
                currentCol = previousCol;
                currentLine = previousLine;
                temp = currentChar;
            } else {
                if (!hasNoChar(currentChar))
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

    private boolean hasNoChar(int character) {
        return character == reader.getEof() || character == '\n';
    }

}
