package com.github.ConcordiaSOEN341.CodeGen;

import java.util.HashMap;

public class CodeGen {
    private HashMap<String, String> instructions;

    public CodeGen() {
        initializeInstructions();
    }

    private void initializeInstructions() {
        instructions = new HashMap<>();
        instructions.put("00", "halt");
        instructions.put("01", "pop");
        instructions.put("02", "dup");
        instructions.put("03", "exit");
        instructions.put("04", "ret");
        instructions.put("0C", "not");
        instructions.put("0D", "and");
        instructions.put("0E", "or");
        instructions.put("0F", "xor");
        instructions.put("10", "neg");
        instructions.put("11", "inc");
        instructions.put("12", "dec");
        instructions.put("13", "add");
        instructions.put("14", "sub");
        instructions.put("15", "mul");
        instructions.put("16", "div");
        instructions.put("17", "rem");
        instructions.put("18", "shl");
        instructions.put("19", "shr");
        instructions.put("1A", "teq");
        instructions.put("1B", "tne");
        instructions.put("1C", "tlt");
        instructions.put("1D", "tgt");
        instructions.put("1E", "tle");
        instructions.put("1F", "tge");
    }

    public String getInstruction(String code) {
        return instructions.get(code);
    }
}
