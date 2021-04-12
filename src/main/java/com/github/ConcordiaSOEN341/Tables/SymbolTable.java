package com.github.ConcordiaSOEN341.Tables;

import com.github.ConcordiaSOEN341.Interfaces.ICompilerMap;

import java.util.HashMap;

public class SymbolTable implements ICompilerMap<String, String> {
    private HashMap<String, String> symbolTable;

    public SymbolTable() {
        initializeMap();
    }

    private void initializeMap() {
        symbolTable = new HashMap<>();
        // Inherent
        symbolTable.put("halt", "00");
        symbolTable.put("pop", "01");
        symbolTable.put("dup", "02");
        symbolTable.put("exit", "03");
        symbolTable.put("ret", "04");
        symbolTable.put("not", "0C");
        symbolTable.put("and", "0D");
        symbolTable.put("or", "0E");
        symbolTable.put("xor", "0F");
        symbolTable.put("neg", "10");
        symbolTable.put("inc", "11");
        symbolTable.put("dec", "12");
        symbolTable.put("add", "13");
        symbolTable.put("sub", "14");
        symbolTable.put("mul", "15");
        symbolTable.put("div", "16");
        symbolTable.put("rem", "17");
        symbolTable.put("shl", "18");
        symbolTable.put("shr", "19");
        symbolTable.put("teq", "1A");
        symbolTable.put("tne", "1B");
        symbolTable.put("tlt", "1C");
        symbolTable.put("tgt", "1D");
        symbolTable.put("tle", "1E");
        symbolTable.put("tge", "1F");
        // Immediate Base
        symbolTable.put("br.i5", "30");
        symbolTable.put("brf.i5", "50");
        symbolTable.put("enter.u5","80");
        symbolTable.put("ldc.i3", "90");
        symbolTable.put("addv.u3", "98");
        symbolTable.put("ldv.u3", "A0");
        symbolTable.put("stv.u3", "A8");
        // Relative
        symbolTable.put("addv.u8", "B0");
        symbolTable.put("ldv.u8", "B1");
        symbolTable.put("stv.u8", "B2");
        symbolTable.put("incv.u8", "B3");
        symbolTable.put("decv.u8", "B4");
        symbolTable.put("enter.u8", "BF");
        symbolTable.put("lda.i16", "D5");
        symbolTable.put("ldc.i8", "D9");
        symbolTable.put("idc.i16", "DA");
        symbolTable.put("idc.i32", "DB");
        symbolTable.put("br.i8", "E0");
        symbolTable.put("br.i16", "E1");
        symbolTable.put("brf.i8", "E3");
        symbolTable.put("call.i16", "E7");
        symbolTable.put("trap", "FF");
    }

    public void addEntry(String k, String v){
        symbolTable.put(k,v);
    }

    public String getValue(String mnemonic) {
        return symbolTable.get(mnemonic);
    }

    public boolean keyExists(String mnemonic) {
        return symbolTable.containsKey(mnemonic);
    }

    public String determineOpCode(String mnemonic, String offsetString) {

        int hexNumber = 0;
        int offset = Integer.parseInt(offsetString);

        if (keyExists(mnemonic)) {
            if (mnemonic.equals("br.i5")) {
                hexNumber = Integer.parseInt("30", 16) + offset;
            } else if (mnemonic.equals("brf.i5")) {
                hexNumber = Integer.parseInt("50", 16) + offset;
            } else if (mnemonic.equals("enter.u5")) {
                if (offset <= 15) {
                    hexNumber = Integer.parseInt("80", 16) + offset;
                } else {
                    hexNumber = Integer.parseInt("60", 16) + offset;
                }
            } else if (mnemonic.equals("ldc.i3")) {
                if (offset >= 0) {
                    hexNumber = Integer.parseInt("90", 16) + offset;
                } else {
                    String bin = Integer.toBinaryString(offset).substring(29);
                    offset = Integer.parseInt(bin, 2);
                    hexNumber = Integer.parseInt("90", 16) + offset;
                }
            } else if (mnemonic.equals("addv.u3")) {
                hexNumber = Integer.parseInt("98", 16) + offset;
            } else if (mnemonic.equals("ldv.u3")) {
                hexNumber = Integer.parseInt("A0", 16) + offset;
            } else if (mnemonic.equals("stv.u3")) {
                hexNumber = Integer.parseInt("A8", 16) + offset;
            }
        } else {
            return getValue(mnemonic);
        }

        return String.format("%2X", hexNumber);
    }
}