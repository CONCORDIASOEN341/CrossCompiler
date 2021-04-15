package com.github.ConcordiaSOEN341.Lexer;

import com.github.ConcordiaSOEN341.Error.Error;
import com.github.ConcordiaSOEN341.Interfaces.*;
import com.github.ConcordiaSOEN341.Logger.LoggerFactory;
import com.github.ConcordiaSOEN341.Logger.LoggerType;
import com.github.ConcordiaSOEN341.Tables.SymbolTable;

import java.util.ArrayList;

public class Lexer implements ILexer {
    private int currentLine = 1;
    private int currentCol = 0;
    private final IReader reader;
    private final SymbolTable symbolTable;
    private final LexerFSM lexerFSM;
    private final IErrorReporter reporter;
    private int stateID = 0;
    private int temp = 0;

    private final ILogger logger = LoggerFactory.getLogger(LoggerType.LEXER);

    public Lexer(SymbolTable s, LexerFSM d, IReader r, IErrorReporter e) {
        symbolTable = s;
        lexerFSM = d;
        reader = r;
        reporter = e;
    }

    @Deprecated
    public ArrayList<IToken> generateTokenList() {
        logger.log("Generating Token List");
        ArrayList<IToken> tokenList = new ArrayList<>();
        IToken t;

        do {
            t = getNextToken();
            tokenList.add(t);

        } while (t.getTokenType() != TokenType.EOF);

        logger.log("Token List Generation Completed");

        return tokenList;
    }

    public IToken getNextToken() {
        Token token = new Token(new Position(currentLine, currentCol, currentCol));
        StringBuilder tokenString = new StringBuilder();
        TokenType type;
        int startCol = 0;
        int line = 0;

        stateID = lexerFSM.getInitialStateID();
        type = TokenType.START;
        boolean tokenStarted = false;
        int currentChar;
        int previousCol;
        int previousLine;
        int previousStateID;

        logger.log("Getting Next Token");

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
            stateID = lexerFSM.getNextStateID(stateID, currentChar);
            type = lexerFSM.getStateType(stateID);

            // TRACK ERRORS
            if (type == TokenType.ERROR) {
                stateID = (stateID == 0) ? previousStateID : stateID;
                reporter.record(new Error(lexerFSM.getErrorType(stateID), new Position(previousLine, previousCol, previousCol + 1)));
                type = lexerFSM.getStateType(stateID);
            }

            if (lexerFSM.isBackTrack(stateID)) {
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
            if (symbolTable.getValue(token.getTokenString()) != null) {
                type = TokenType.MNEMONIC;
            } else {
                type = TokenType.LABEL;
            }
        }

        token.setTokenType(type);
        logger.log("Token found setting type " + type.toString());

        return token;

    }

    private boolean hasNoChar(int character) {
        return character == reader.getEof() || character == '\n';
    }

}
