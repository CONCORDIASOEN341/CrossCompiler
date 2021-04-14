package com.github.ConcordiaSOEN341.Interfaces;

import com.github.ConcordiaSOEN341.Tables.OpCodeTableElement;

import java.util.ArrayList;
import java.util.HashMap;

public interface ICodeGen {
    void generateListingFile(String fileName);

    void generateExe(String fileName);

    String[] listing();

    HashMap<Integer, IOpCodeTableElement> generateOpCodeTable();
}
