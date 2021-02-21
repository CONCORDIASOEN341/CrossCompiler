package com.github.ConcordiaSOEN341.CodeGenMaps;

import java.util.HashMap;

public class StateMap implements ICompilerMap<Integer,String>{
    private HashMap<Integer, String> stateMap;

    public StateMap() {
        initializeMap();
    }

    public void initializeMap() {
        stateMap = new HashMap<>();
        stateMap.put(0, "Error");
        stateMap.put(1, "Start");
        stateMap.put(2, "Start");
        stateMap.put(3, "Identifier");
    }

    public String getValue(Integer id) {
        return stateMap.get(id);
    }
}
