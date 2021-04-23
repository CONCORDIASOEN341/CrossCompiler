package com.github.ConcordiaSOEN341.Parser;

import java.util.ArrayList;

public class OpCodeTableElement implements IOpCodeTableElement {
    private int line;
    private String address;
    private String opCode;
    private final ArrayList<String> operands;
    private int bitSpace;
    private String label = null;

    public OpCodeTableElement() {
        operands = new ArrayList<>();
        opCode = "";
    }

    public OpCodeTableElement(int line, String address, String opCode, int bitSpace, String label) {
        this.line = line;
        this.address = address;
        this.opCode = opCode;
        this.operands = new ArrayList<>();
        this.bitSpace = bitSpace;
        this.label = label;
    }

    public int getBitSpace() {
        return bitSpace;
    }

    public void setBitSpace(int bitSpace) {
        this.bitSpace = bitSpace;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOpCode() {
        return opCode;
    }

    public void setOpCode(String opCode) {
        this.opCode = opCode;
    }

    public ArrayList<String> getOperands() {
        return operands;
    }

    public void addOperand(String op) {
        operands.add(op);
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String toString() {
        return line + " " + address + " " + opCode + " " + operands.toString() + " " + bitSpace + " " + label;
    }


}
