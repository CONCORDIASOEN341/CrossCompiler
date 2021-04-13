package com.github.ConcordiaSOEN341.Parser;

import com.github.ConcordiaSOEN341.Error.ErrorType;
import com.github.ConcordiaSOEN341.Lexer.TokenType;

import java.util.HashMap;

public class ParserFSM {
    private HashMap<Integer, HashMap<TokenType,Integer>> transitions;
    private HashMap<Integer, ErrorType> parserErrorTable;

    public ParserFSM(){
        initializeErrorTable();
        initializeMap();
    }

    private void initializeErrorTable(){
    }

    private void initializeMap(){
        transitions = new HashMap<>();

        transitions.put(1, new HashMap<>());

        transitions.get(1).put(TokenType.LABEL, 2);
        transitions.get(1).put(TokenType.MNEMONIC, 3);
        transitions.get(1).put(TokenType.DIRECTIVE, 4);
        transitions.get(1).put(TokenType.COMMENT, 5);
        transitions.get(1).put(TokenType.EOL, 6);
        transitions.get(1).put(TokenType.EOF, 6);

        transitions.put(2, new HashMap<>());

        transitions.get(2).put(TokenType.MNEMONIC, 3);
        transitions.get(2).put(TokenType.DIRECTIVE, 4);
        transitions.get(2).put(TokenType.COMMENT, 5);
        transitions.get(2).put(TokenType.EOL, 6);
        transitions.get(2).put(TokenType.EOF, 6);

        transitions.put(3, new HashMap<>());

        transitions.get(3).put(TokenType.OFFSET, 7);
        transitions.get(3).put(TokenType.LABEL, 7);
        transitions.get(3).put(TokenType.COMMENT, 5);
        transitions.get(3).put(TokenType.EOL, 6);
        transitions.get(3).put(TokenType.EOF, 6);

        transitions.put(4, new HashMap<>());

        transitions.get(4).put(TokenType.CSTRING, 8);

        transitions.put(5, new HashMap<>());

        transitions.get(5).put(TokenType.EOL, 6);
        transitions.get(5).put(TokenType.EOF, 6);

        transitions.put(7, new HashMap<>());

        transitions.get(7).put(TokenType.COMMENT, 5);
        transitions.get(7).put(TokenType.EOL, 6);
        transitions.get(7).put(TokenType.EOF, 6);

        transitions.put(8, new HashMap<>());

        transitions.get(8).put(TokenType.COMMENT, 5);
        transitions.get(8).put(TokenType.EOL, 6);
        transitions.get(8).put(TokenType.EOF, 6);
    }

    public int getNextStateID(int id, TokenType type){
        return transitions.get(id).getOrDefault(type,0);
    }

    public int getInitialStateID(){
        return (int) transitions.keySet().toArray()[0];
    }

    public ErrorType getErrorType(int id){
        return parserErrorTable.get(id);
    }


}
