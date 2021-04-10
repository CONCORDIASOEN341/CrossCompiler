package com.github.ConcordiaSOEN341.Interfaces;

import com.github.ConcordiaSOEN341.Tables.OpCodeTableElement;

import java.util.ArrayList;
import java.util.HashMap;

public interface ICodeGen {
    void generateListingFile(String fileName, ArrayList<ILineStatement> ir, IErrorReporter e);

    String[] listing(ArrayList<ILineStatement> ir);

    HashMap<Integer, IOpCodeTableElement> generateOpCodeTable(ArrayList<ILineStatement> ir);
}
