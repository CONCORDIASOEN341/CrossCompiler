package com.github.ConcordiaSOEN341.Parser;

import com.github.ConcordiaSOEN341.Error.ErrorType;
import com.github.ConcordiaSOEN341.Lexer.TokenType;
import com.github.ConcordiaSOEN341.Logger.ILogger;
import com.github.ConcordiaSOEN341.Logger.LoggerFactory;
import com.github.ConcordiaSOEN341.Logger.LoggerType;

import java.util.HashMap;

public class ParserFSM {
    private HashMap<Integer, HashMap<TokenType, Integer>> transitions;
    private HashMap<Integer, ErrorType> parserErrorTable;

    private final ILogger logger;

    public ParserFSM(LoggerFactory lf) {
        logger = lf.getLogger(LoggerType.PARSER);
        initializeErrorTable();
        initializeMap();
    }

    private void initializeErrorTable() {
        logger.log("Parser Finite State Machine initializing error table...");
        parserErrorTable = new HashMap<>();
        parserErrorTable.put(4, ErrorType.EXTRA_OPERAND);
        parserErrorTable.put(3, ErrorType.MISSING_OPERAND);
        parserErrorTable.put(10, ErrorType.INVALID_SIGNED_3BIT_OPERAND);
        parserErrorTable.put(11, ErrorType.INVALID_UNSIGNED_3BIT_OPERAND);
        parserErrorTable.put(13, ErrorType.INVALID_SIGNED_4BIT_OPERAND);
        parserErrorTable.put(14, ErrorType.INVALID_UNSIGNED_4BIT_OPERAND);
        parserErrorTable.put(16, ErrorType.INVALID_SIGNED_5BIT_OPERAND);
        parserErrorTable.put(17, ErrorType.INVALID_UNSIGNED_5BIT_OPERAND);
        parserErrorTable.put(25, ErrorType.INVALID_SIGNED_8BIT_OPERAND);
        parserErrorTable.put(26, ErrorType.INVALID_UNSIGNED_8BIT_OPERAND);
        // Missing Errors
//        parserErrorTable.put(49, ErrorType.INVALID_SIGNED_16BIT_OPERAND);
//        parserErrorTable.put(97, ErrorType.INVALID_SIGNED_32BIT_OPERAND);
        logger.log("Parser Finite State Machine error table initialized");
    }

    private void initializeMap() {
        logger.log("Parser Finite State Machine initializing map...");
        transitions = new HashMap<>();

        transitions.put(1, new HashMap<>());

        transitions.get(1).put(TokenType.LABEL, 2);
        transitions.get(1).put(TokenType.MNEMONIC, 3);
        transitions.get(1).put(TokenType.DIRECTIVE, 5);
        transitions.get(1).put(TokenType.COMMENT, 6);
        transitions.get(1).put(TokenType.EOL, 7);
        transitions.get(1).put(TokenType.EOF, 7);

        transitions.put(2, new HashMap<>());

        transitions.get(2).put(TokenType.MNEMONIC, 3);
        transitions.get(2).put(TokenType.DIRECTIVE, 5);
        transitions.get(2).put(TokenType.COMMENT, 6);
        transitions.get(2).put(TokenType.EOL, 7);
        transitions.get(2).put(TokenType.EOF, 7);

        transitions.put(3, new HashMap<>());

        transitions.get(3).put(TokenType.OFFSET, 8);
        transitions.get(3).put(TokenType.LABEL, 8);

        transitions.put(4, new HashMap<>());

        transitions.get(4).put(TokenType.COMMENT, 6);
        transitions.get(4).put(TokenType.EOL, 7);
        transitions.get(4).put(TokenType.EOF, 7);

        transitions.put(5, new HashMap<>());

        transitions.get(5).put(TokenType.CSTRING, 9);

        transitions.put(6, new HashMap<>());

        transitions.get(6).put(TokenType.EOL, 7);
        transitions.get(6).put(TokenType.EOF, 7);

        transitions.put(8, new HashMap<>());

        transitions.get(8).put(TokenType.COMMENT, 6);
        transitions.get(8).put(TokenType.EOL, 7);
        transitions.get(8).put(TokenType.EOF, 7);

        transitions.put(9, new HashMap<>());

        transitions.get(9).put(TokenType.COMMENT, 6);
        transitions.get(9).put(TokenType.EOL, 7);
        transitions.get(9).put(TokenType.EOF, 7);
        logger.log("Parser Finite State Machine map initialized");
    }

    public int getNextStateID(int id, TokenType type) {
        return transitions.get(id).getOrDefault(type, 0);
    }

    public int getInitialStateID() {
        return (int) transitions.keySet().toArray()[0];
    }

    public ErrorType getErrorType(int id) {
        return parserErrorTable.get(id);
    }


}
