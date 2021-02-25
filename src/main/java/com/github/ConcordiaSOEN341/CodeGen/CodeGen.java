package com.github.ConcordiaSOEN341.CodeGen;

import com.github.ConcordiaSOEN341.Interfaces.ICodeGen;
import com.github.ConcordiaSOEN341.Interfaces.ILineStatement;
import com.github.ConcordiaSOEN341.Maps.CodeMap;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CodeGen implements ICodeGen {

    public void generateListingFile(String fileName,ArrayList<ILineStatement> ir){
        String listFile = fileName.substring(0,fileName.length()-4) + ".lst";
        try{
            FileWriter listingWriter = new FileWriter(listFile);
            listingWriter.write("Line Addr Code \t\t\tLabel \t\t  Mne \tOperand \t  Comments\n");

            String[] listings = listing(ir);

            for (String listing : listings) {
                listingWriter.write(listing);
            }

            listingWriter.close();
        }catch(IOException e){
            System.out.println("An error occurred");
            System.out.println("The program will terminate.");
            e.printStackTrace();
            System.exit(0);
        }
    }

    public String[] listing(ArrayList<ILineStatement> ir){
        CodeMap codeGen = new CodeMap();
        String[] listings = new String[ir.size()];

        for(int i = 0; i < ir.size(); i ++) {
            String hexAddress = String.format("%04X", i);
            String mnemonic = ir.get(i).getInstruction().toString();
            String codeMnemonic = codeGen.getValue(mnemonic);
            listings[i] = ((i + 1) + "\t " + hexAddress + " " + codeMnemonic + " \t\t\t  \t\t\t  " + mnemonic + " \t\t \t\t\t \t\n");
        }
        return listings;
    }


}
