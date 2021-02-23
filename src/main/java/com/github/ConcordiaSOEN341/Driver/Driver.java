package com.github.ConcordiaSOEN341.Driver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.github.ConcordiaSOEN341.CodeGenMaps.CodeMap;
import com.github.ConcordiaSOEN341.Lexer.Lexer;
import com.github.ConcordiaSOEN341.Lexer.Token;
import com.github.ConcordiaSOEN341.Lexer.TokenType;
import com.github.ConcordiaSOEN341.Parser.Parser;

import java.util.ArrayList;

public class Driver {
    public static void main(String[] args) {

        String fileName = "src/TestInherentMnemonics.asm";

        Lexer lexer = new Lexer(fileName);
        ArrayList<Token> tokenList = new ArrayList<>(); // Use this for listing file/parsing
        Token t;
        Parser test = new Parser();
        do{
            t = lexer.getNextToken();
            System.out.println(t);

            tokenList.add(t);

        }while(t.getTokenType() != TokenType.EOF);


        test.generateIR(tokenList);

        CodeMap codeGen = new CodeMap();
        try{
            File listingFile = new File ("testInherentMnemonics.lst");

            //Not sure if prof would want these implemented or not...
            //if (listingFile.createNewFile()){
            //  System.out.println("File created");
            //}else{
            //  System.out.println("File alreadyExists.");
            //}

            FileWriter listingWriter = new FileWriter("testInherentMnemonics.lst");
            listingWriter.write("Line Addr Code \t\t\tLabel \t\t  Mne \tOperand \t  Comments\n");

            for(int i = 0; i < test.getIntermediateRep().size(); i ++) {
                String hexAddress = String.format("%04X", i);
                String codeMnemonic = codeGen.getValue(test.getIntermediateRep().get(i).getInstruction().toString());
                listingWriter.write((i + 1) + "\t " + hexAddress + " " + codeMnemonic + " \t\t\t  \t\t\t  " + test.getIntermediateRep().get(i).getInstruction() + " \t\t \t\t\t \t\n");
            }
            listingWriter.close();

        }catch(IOException e){
            System.out.println("An error occurred");
            e.printStackTrace();
        }


    }

}


