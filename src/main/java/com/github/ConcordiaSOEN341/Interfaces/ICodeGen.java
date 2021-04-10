package com.github.ConcordiaSOEN341.Interfaces;

import java.util.ArrayList;

public interface ICodeGen {
    void generateListingFile(String fileName, ArrayList<ILineStatement> ir, IErrorReporter e);

    String[] listing(ArrayList<ILineStatement> ir);

    void generateOpCodeTable(ArrayList<ILineStatement> ir);
}
