package com.github.ConcordiaSOEN341.CodeGen;

import com.github.ConcordiaSOEN341.Parser.ILineStatement;

import java.util.ArrayList;

public interface ICodeGen {

    void setIR(ArrayList<ILineStatement> ir);

    void generateListingFile(String fileName);

    String[] listingOP();

    String[] listingIRLabel();

    String[] listingIRMne();

    String[] listingIROps();

    String[] listingIRComments();

    void generateExe(String fileName);

    ArrayList<IOpCodeTableElement> generateOpCodeTable();
}
