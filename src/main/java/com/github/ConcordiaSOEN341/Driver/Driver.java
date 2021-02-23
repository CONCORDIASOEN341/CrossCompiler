package com.github.ConcordiaSOEN341.Driver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

        System.out.println(test.generateIR(tokenList).toString());


        try{
            File listingFile = new File ("testInherentMnemonics.lst");
            //if (listingFile.createNewFile()){
                //System.out.println("File created");
            //}else{
                //System.out.println("File alreadyExists.");
            //}
            FileWriter listingWriter = new FileWriter("testInherentMnemonics.lst");
            listingWriter.write("Line Addr Code \t\t\tLabel \t\t  Mne \tOperand \t  Comments\n");

            String[] tokenInLine = {"","",""};
            for(int i = 0; i < tokenList.size(); i ++) {
                int lineNumber = tokenList.get(i).getLine();

                if (tokenList.get(i).getTokenType() == TokenType.MNEMONIC){
                    tokenInLine[0] = tokenList.get(i).getTokenString();
                }if (tokenList.get(i).getTokenType() == TokenType.LABEL){
                    tokenInLine[1] = tokenList.get(i).getTokenString();
                }if (tokenList.get(i).getTokenType() == TokenType.COMMENT){
                    tokenInLine[2] = tokenList.get(i).getTokenString();
                }
                if(tokenList.get(i).getTokenType()== TokenType.EOF||tokenList.get(i).getTokenType() == TokenType.EOL){
                    String hexAddress = String.format("%04X", lineNumber-1);
                    listingWriter.write(lineNumber +"\t "+hexAddress+" Code \t\t\t"+tokenInLine[1]+" \t\t\t  "+tokenInLine[0]+" \t\t \t\t\t "+tokenInLine[2]+"\n");
                    tokenInLine[0]="";
                    tokenInLine[1]="";
                    tokenInLine[2]="";
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


