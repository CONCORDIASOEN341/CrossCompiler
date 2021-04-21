package com.github.ConcordiaSOEN341.Parser;

import com.github.ConcordiaSOEN341.CodeGen.IOpCodeTableElement;

import java.util.ArrayList;

public interface IParser {
    ArrayList<ILineStatement> getIR();

    void setIR(ArrayList<ILineStatement> ir);

    ArrayList<IOpCodeTableElement> getOpCodeTable();

    void parse();

    ArrayList<ILineStatement> generateIR();

    ArrayList<IOpCodeTableElement> generateOpCodeTable();
}
