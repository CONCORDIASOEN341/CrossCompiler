package com.github.ConcordiaSOEN341.Driver;

import com.github.ConcordiaSOEN341.Lexer.Lexer;
import com.github.ConcordiaSOEN341.Lexer.Token;
import com.github.ConcordiaSOEN341.Lexer.TokenType;
import com.github.ConcordiaSOEN341.Reader.IReader;
import com.github.ConcordiaSOEN341.Reader.Reader;
import com.github.ConcordiaSOEN341.Parser.Parser;

import java.util.ArrayList;

public class Driver {
    public static void main(String[] args) {

        String fileName = "src/TestInherentMnemonics.asm";

        IReader reader = new Reader(fileName);

        Lexer lexer = new Lexer(reader);
        ArrayList<Token> tokenList = new ArrayList<>(); // Use this for listing file/parsing
        Token t;
        Parser test = new Parser();
        do{
            t = lexer.getNextToken();
            System.out.println(t);

            tokenList.add(t);

        }while(t.getTokenType() != TokenType.EOF);

        System.out.println(test.generateIR(tokenList).toString());

    }
}