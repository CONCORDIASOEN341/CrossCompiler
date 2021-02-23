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
            listingWriter.write("Line Addr Code \t\t\tLabel \t\t  Mne \tOperand \t Comments\n");

            String[] TokenInLine = new String[5];
            for(int i = 0; i < tokenList.size(); i ++) {
                int lineNumber = tokenList.get(i).getLine();

                if(tokenList.size() == i+1){
                    break;
                }

                if(tokenList.get(i).getStartColumn() == 1){
                    TokenInLine[0]= tokenList.get(i).getTokenString();
                }else if (tokenList.get(i).getStartColumn() == 1){

                }else if (tokenList.get(i).getStartColumn() == 1){

                }else if (tokenList.get(i).getStartColumn() == 1){

                }else if (tokenList.get(i).getStartColumn() == 1){

                }

                if(tokenList.get(i+1).getLine() != lineNumber){
                    listingWriter.write(lineNumber +"\t "+Integer.toHexString(lineNumber)+" Code \t\t\tLabel \t\t  Mne \tOperand \t Comments");
                    listingWriter.write("\n");
                    continue;
                }
            }





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

