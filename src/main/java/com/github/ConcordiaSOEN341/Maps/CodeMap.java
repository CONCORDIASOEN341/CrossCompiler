package com.github.ConcordiaSOEN341.Maps;

import com.github.ConcordiaSOEN341.Interfaces.ICompilerMap;

import java.util.HashMap;

public class CodeMap implements ICompilerMap<String, String> {
    private HashMap<String, String> instructions;

    public CodeMap() {
        initializeMap();
    }

    public void initializeMap() {
        instructions = new HashMap<>();
        instructions.put("halt", "00");
        instructions.put("pop", "01");
        instructions.put("dup", "02");
        instructions.put("exit", "03");
        instructions.put("ret", "04");
        instructions.put("not", "0C");
        instructions.put("and", "0D");
        instructions.put("or", "0E");
        instructions.put("xor", "0F");
        instructions.put("neg", "10");
        instructions.put("inc", "11");
        instructions.put("dec", "12");
        instructions.put("add", "13");
        instructions.put("sub", "14");
        instructions.put("mul", "15");
        instructions.put("div", "16");
        instructions.put("rem", "17");
        instructions.put("shl", "18");
        instructions.put("shr", "19");
        instructions.put("teq", "1A");
        instructions.put("tne", "1B");
        instructions.put("tlt", "1C");
        instructions.put("tgt", "1D");
        instructions.put("tle", "1E");
        instructions.put("tge", "1F");
    }

    public String getValue(String mnemonic) {
        return instructions.get(mnemonic);
    }
}
