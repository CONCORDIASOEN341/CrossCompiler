package com.github.ConcordiaSOEN341.Interfaces;

import java.util.ArrayList;

public interface IOpCodeTableElement {
    int getBitSpace();


    void setBitSpace(int bitSpace);

    int getLine();

    void setLine(int line);

    String getAddress();

    void setAddress(String address);

    String getOpCode();

    void setOpCode(String opCode);

    ArrayList<String> getOperands();

    void addOperand(String op);

    String getLabel();

    void setLabel(String label);
}
