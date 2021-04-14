package com.github.ConcordiaSOEN341.Interfaces;

import java.util.ArrayList;

public interface ICodeGen {

    void setIR(ArrayList<ILineStatement> ir);

    void generateListingFile(String fileName);

    void generateExe(String fileName);

    String[] listing();

    String generateByteCode();

    ArrayList<IOpCodeTableElement> generateOpCodeTable();
}
