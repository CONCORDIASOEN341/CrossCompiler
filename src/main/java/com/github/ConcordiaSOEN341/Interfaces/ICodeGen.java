package com.github.ConcordiaSOEN341.Interfaces;

import com.github.ConcordiaSOEN341.Parser.LineStatement;

import java.util.ArrayList;

public interface ICodeGen {
    void generateListingFile(String fileName, ArrayList<LineStatement> ir);
}