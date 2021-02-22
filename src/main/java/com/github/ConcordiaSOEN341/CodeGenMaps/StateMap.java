package com.github.ConcordiaSOEN341.CodeGenMaps;

import com.github.ConcordiaSOEN341.Lexer.TokenType;

import java.util.HashMap;

public class StateMap implements ICompilerMap<Integer, TokenType>{
    private HashMap<Integer, TokenType> stateMap;

    public StateMap() {
        initializeMap();
    }

    public void initializeMap() {
        stateMap = new HashMap<>();
        stateMap.put(0, TokenType.ERROR);
        stateMap.put(1, TokenType.START);
        stateMap.put(2, TokenType.START);
        stateMap.put(3, TokenType.IDENTIFIER);
    }

    public TokenType getValue(Integer id) {
        return stateMap.get(id);
    }
}
