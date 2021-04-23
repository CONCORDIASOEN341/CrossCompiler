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
    private final int FINAL_STATE_ID = 7;
    private final int INIT_STATE_ID = 1;

    private final ILogger logger;

    public ParserFSM(LoggerFactory lf) {
        logger = lf.getLogger(LoggerType.PARSER);
        initializeErrorTable();
        initializeMap();
    }

    private void initializeErrorTable() {
        logger.log("Parser Finite State Machine initializing error table...");
        parserErrorTable = new HashMap<>();
        parserErrorTable.put(2, ErrorType.SYNTAX_ERROR);
        parserErrorTable.put(3, ErrorType.MISSING_OPERAND);
        parserErrorTable.put(4, ErrorType.EXTRA_OPERAND);
        parserErrorTable.put(5, ErrorType.CSTRING_NOT_FOUND);
        parserErrorTable.put(6, ErrorType.SYNTAX_ERROR);
        parserErrorTable.put(8, ErrorType.SYNTAX_ERROR);
        parserErrorTable.put(10, ErrorType.INVALID_SIGNED_3BIT_OPERAND);
        parserErrorTable.put(11, ErrorType.INVALID_UNSIGNED_3BIT_OPERAND);
        parserErrorTable.put(13, ErrorType.INVALID_SIGNED_4BIT_OPERAND);
        parserErrorTable.put(14, ErrorType.INVALID_UNSIGNED_4BIT_OPERAND);
        parserErrorTable.put(16, ErrorType.INVALID_SIGNED_5BIT_OPERAND);
        parserErrorTable.put(17, ErrorType.INVALID_UNSIGNED_5BIT_OPERAND);
        parserErrorTable.put(25, ErrorType.INVALID_SIGNED_8BIT_OPERAND);
        parserErrorTable.put(26, ErrorType.INVALID_UNSIGNED_8BIT_OPERAND);
        parserErrorTable.put(50, ErrorType.INVALID_UNSIGNED_16BIT_OPERAND);
        parserErrorTable.put(66, ErrorType.INVALID_DIRECTIVE);
        parserErrorTable.put(69, ErrorType.LABEL_NOT_FOUND);
        parserErrorTable.put(420, ErrorType.LABEL_DEFINED);
        parserErrorTable.put(42069, ErrorType.INVALID_OPERAND_LABEL_NOT_USED);
        logger.log("Parser Finite State Machine error table initialized");
    }

    private void initializeMap() {
        logger.log("Parser Finite State Machine initializing map...");
        transitions = new HashMap<>();

        // Intermediate State
        transitions.put(INIT_STATE_ID, new HashMap<>());

        transitions.get(INIT_STATE_ID).put(TokenType.LABEL, 2);
        transitions.get(INIT_STATE_ID).put(TokenType.MNEMONIC, 3);
        transitions.get(INIT_STATE_ID).put(TokenType.DIRECTIVE, 5);
        transitions.get(INIT_STATE_ID).put(TokenType.COMMENT, 6);
        transitions.get(INIT_STATE_ID).put(TokenType.EOL, FINAL_STATE_ID);
        transitions.get(INIT_STATE_ID).put(TokenType.EOF, FINAL_STATE_ID);

        // Label State
        transitions.put(2, new HashMap<>());

        transitions.get(2).put(TokenType.MNEMONIC, 3);
        transitions.get(2).put(TokenType.DIRECTIVE, 5);
        transitions.get(2).put(TokenType.COMMENT, 6);
        transitions.get(2).put(TokenType.EOL, FINAL_STATE_ID);
        transitions.get(2).put(TokenType.EOF, FINAL_STATE_ID);

        // Mnemonic State (Immediate or Relative)
        transitions.put(3, new HashMap<>());

        transitions.get(3).put(TokenType.OFFSET, 8);
        transitions.get(3).put(TokenType.LABEL, 8);

        // Mnemonic State (Inherent only)
        transitions.put(4, new HashMap<>());

        transitions.get(4).put(TokenType.COMMENT, 6);
        transitions.get(4).put(TokenType.EOL, FINAL_STATE_ID);
        transitions.get(4).put(TokenType.EOF, FINAL_STATE_ID);

        // Directive State
        transitions.put(5, new HashMap<>());

        transitions.get(5).put(TokenType.CSTRING, 8);

        // Comment State
        transitions.put(6, new HashMap<>());

        transitions.get(6).put(TokenType.EOL, 7);
        transitions.get(6).put(TokenType.EOF, 7);

        // After Instruction State
        transitions.put(8, new HashMap<>());

        transitions.get(8).put(TokenType.COMMENT, 6);
        transitions.get(8).put(TokenType.EOL, FINAL_STATE_ID);
        transitions.get(8).put(TokenType.EOF, FINAL_STATE_ID);

        logger.log("Parser Finite State Machine map initialized");
    }

    public int getNextStateID(int id, TokenType type) {
        return transitions.get(id).getOrDefault(type, 0);
    }

    public int getInitialStateID() {
        return INIT_STATE_ID;
    }

    public int getFinalStateID() {
        return FINAL_STATE_ID;
    }

    public ErrorType getErrorType(int id) {
        return parserErrorTable.get(id);
    }


}
