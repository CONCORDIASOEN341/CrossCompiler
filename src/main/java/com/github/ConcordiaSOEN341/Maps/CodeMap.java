package com.github.ConcordiaSOEN341.Maps;

import com.github.ConcordiaSOEN341.Interfaces.ICompilerMap;

import java.util.HashMap;

public class CodeMap implements ICompilerMap<String, String> {
    private HashMap<String, String> instructions;

    public CodeMap() {
        initializeMap();
    }

    private void initializeMap() {
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

    public boolean keyExists(String mnemonic) {
        return instructions.containsKey(mnemonic);
    }

    public String determineOpCode(String mnemonic, String offsetString) {

        String opCode = "";
        int hexNumber = 0;
        int offset = Integer.parseInt(offsetString);

        if (!keyExists(mnemonic)) {
            if (mnemonic.equals("br.i5")) {
                hexNumber = Integer.parseInt("30", 16) + offset;

            } else if (mnemonic.equals("brf.i5")) {
                hexNumber = Integer.parseInt("50", 16) + offset;

            } else if (mnemonic.equals("enter.u5")) {
                if(offset < 15){
                    hexNumber = Integer.parseInt("80", 16) + offset;
                }else{
                    hexNumber = Integer.parseInt("70", 16) + offset;
                }

            } else if (mnemonic.equals("idc.i3")) {
                hexNumber = Integer.parseInt("90", 16) + offset;

            } else if (mnemonic.equals("addv.u3")) {
                hexNumber = Integer.parseInt("98", 16) + offset;

            } else if (mnemonic.equals("ldv.u3")) {
                hexNumber = Integer.parseInt("A0", 16) + offset;
                opCode = "lol nah G";

            } else if (mnemonic.equals("stv.u3")) {
                hexNumber = Integer.parseInt("A8", 16) + offset;
            }
        } else {
            opCode = getValue(mnemonic);
        }

        opCode = String.format("%4X", hexNumber);

        return opCode;
    }

}
