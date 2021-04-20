package com.github.ConcordiaSOEN341.CodeGen;

import com.github.ConcordiaSOEN341.Parser.ILineStatement;

import java.util.ArrayList;

public interface ICodeGen {

    void generateListingFile(String fileName, ArrayList<ILineStatement> iR, ArrayList<IOpCodeTableElement> opCodeTable);

    void generateCode(String fileName, ArrayList<ILineStatement> iR, ArrayList<IOpCodeTableElement> opCodeTable);

    String[] listingOP(ArrayList<ILineStatement> iR, ArrayList<IOpCodeTableElement> opCodeTable);

    String[] listingIRLabel(ArrayList<ILineStatement> iR);

    String[] listingIRMne(ArrayList<ILineStatement> iR);

    String[] listingIROps(ArrayList<ILineStatement> iR);

    String[] listingIRComments(ArrayList<ILineStatement> iR);

    void generateExe(String fileName, ArrayList<IOpCodeTableElement> opCodeTable);
}
