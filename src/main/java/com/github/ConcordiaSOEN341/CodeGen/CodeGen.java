package com.github.ConcordiaSOEN341.CodeGen;

import com.github.ConcordiaSOEN341.Interfaces.ICodeGen;
import com.github.ConcordiaSOEN341.Maps.CodeMap;
import com.github.ConcordiaSOEN341.Parser.LineStatement;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CodeGen implements ICodeGen {

    public void generateListingFile(String fileName,ArrayList<LineStatement> ir){
        String listFile = fileName.substring(0,fileName.length()-4) + ".lst";
        try{
            File listingFile = new File (listFile);

            //Not sure if prof would want these implemented or not...
            //if (listingFile.createNewFile()){
            //  System.out.println("File created");
            //}else{
            //  System.out.println("File alreadyExists.");
            //}

            FileWriter listingWriter = new FileWriter(listFile);
            listingWriter.write("Line Addr Code \t\t\tLabel \t\t  Mne \tOperand \t  Comments\n");

            String[] listings = listing(ir);

            for(int i =0; i < listings.length; i++){
                listingWriter.write(listings[i].toString());
            }

            listingWriter.close();
        }catch(IOException e){
            System.out.println("An error occurred");
            e.printStackTrace();
        }
    }

    public String[] listing(ArrayList<LineStatement> ir){
        CodeMap codeGen = new CodeMap();
        String[] listings = new String[ir.size()];

        for(int i = 0; i < ir.size(); i ++) {
            String hexAddress = String.format("%04X", i);
            String codeMnemonic = codeGen.getValue(ir.get(i).getInstruction().toString());
            listings[i] = ((i + 1) + "\t " + hexAddress + " " + codeMnemonic + " \t\t\t  \t\t\t  " + ir.get(i).getInstruction() + " \t\t \t\t\t \t\n");
        }

        return listings;
    }

}
