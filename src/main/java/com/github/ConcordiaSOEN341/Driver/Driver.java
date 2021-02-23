package com.github.ConcordiaSOEN341.Driver;

import com.github.ConcordiaSOEN341.CommandHandle.CommandHandle;
import com.github.ConcordiaSOEN341.Lexer.Lexer;
import com.github.ConcordiaSOEN341.Lexer.Token;
import com.github.ConcordiaSOEN341.Lexer.TokenType;
import com.github.ConcordiaSOEN341.Parser.Parser;

import java.util.ArrayList;

import static java.lang.System.exit;

public class Driver {
    public static void main(String[] args) {


        CommandHandle commandHandle = new CommandHandle(args);
        String file = commandHandle.getFile();


        Lexer lexer = new Lexer(file);
        ArrayList<Token> tokenList = new ArrayList<>(); // Use this for listing file/parsing
        Token t;
        Parser test = new Parser();
        do{
            t = lexer.getNextToken();
            System.out.println(t);

            tokenList.add(t);

        }while(t.getTokenType() != TokenType.EOF);

        System.out.println(test.generateIR(tokenList).toString());

        commandHandle.delete();

    }
}