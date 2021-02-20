package com.github.ConcordiaSOEN341.Driver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Driver {
    public static void main(String[] args) {
        System.out.println("Driver is working");




        try{
            File listingFile = new File ("testInherentMnemonics.lst");
            if (listingFile.createNewFile()){
                //System.out.println("File created");
            }else{
                //System.out.println("File alreadyExists.");
            }
            FileWriter listingWriter = new FileWriter("testInherentMnemonics.lst");
            listingWriter.write("Line Addr Code \t\t\tLabel \t\t  Mne \tOperand \t Comments");
            listingWriter.close();

        }catch(IOException e){
            System.out.println("An error occurred");
            e.printStackTrace();
        }
    }

    public String extraWords(){



        return "jajaja";
    }
}

