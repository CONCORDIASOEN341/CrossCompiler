package com.github.ConcordiaSOEN341.Interfaces;

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

    String generateByteCode();

    ArrayList<IOpCodeTableElement> generateOpCodeTable();
}
