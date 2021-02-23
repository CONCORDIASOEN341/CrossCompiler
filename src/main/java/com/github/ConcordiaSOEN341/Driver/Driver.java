package com.github.ConcordiaSOEN341.Driver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import com.github.ConcordiaSOEN341.Lexer.Lexer;
import com.github.ConcordiaSOEN341.Lexer.Token;
import com.github.ConcordiaSOEN341.Lexer.TokenType;

import java.util.ArrayList;

public class Driver {
    public static void main(String[] args) {

        String fileName = "src/TestInherentMnemonics.asm";

        Lexer lexer = new Lexer(fileName);
        ArrayList<Token> tokenList = new ArrayList<>(); // Use this for listing file/parsing
        Token t;

        do{
            t = lexer.getNextToken();
            System.out.println(t);

            tokenList.add(t);

        }while(t.getTokenType() != TokenType.EOF);



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

