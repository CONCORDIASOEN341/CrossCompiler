package com.github.ConcordiaSOEN341.Parser;

import java.util.ArrayList;

public interface IParser {
    ArrayList<ILineStatement> getIR();

    void setIR(ArrayList<ILineStatement> ir);

    ArrayList<IOpCodeTableElement> getOpCodeTable();

    void parse();

    ArrayList<ILineStatement> generateIR();

    ArrayList<IOpCodeTableElement> generateOpCodeTable();
}
