package com.github.ConcordiaSOEN341.Maps;

import com.github.ConcordiaSOEN341.Interfaces.ICompilerMap;
import com.github.ConcordiaSOEN341.Lexer.TokenType;

import java.util.HashMap;

public class StateMap implements ICompilerMap<Integer, TokenType> {
    private HashMap<Integer, TokenType> stateMap;

    public StateMap() {
        initializeMap();
    }

    private void initializeMap() {
        stateMap = new HashMap<>();
        stateMap.put(0, TokenType.ERROR);

        stateMap.put(1, TokenType.START);

        //Identifier
        stateMap.put(2, TokenType.START);
        stateMap.put(3, TokenType.IDENTIFIER);

        //Comments
        stateMap.put(4, TokenType.START);
        stateMap.put(5, TokenType.COMMENT);

        //NewLine and EOF
        stateMap.put(6, TokenType.EOL);
        stateMap.put(7, TokenType.EOF);

        //Number
        stateMap.put(8, TokenType.START);
        stateMap.put(9, TokenType.OFFSET);

    }

    public TokenType getValue(Integer id) {
        return stateMap.get(id);
    }
}
