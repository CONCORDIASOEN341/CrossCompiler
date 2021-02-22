package com.github.ConcordiaSOEN341.Driver;

import com.github.ConcordiaSOEN341.Lexer.Lexer;
import com.github.ConcordiaSOEN341.Lexer.Token;
import com.github.ConcordiaSOEN341.Lexer.TokenType;
import com.github.ConcordiaSOEN341.Parser.Parser;


import java.util.ArrayList;

public class Driver {
    public static void main(String[] args) {

        Parser test = new Parser();
        System.out.println(test.generateIR("src/TestInherentMnemonics.asm").toString());



    }
}
