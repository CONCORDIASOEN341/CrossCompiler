package com.github.ConcordiaSOEN341.Driver;

import com.github.ConcordiaSOEN341.CodeGen.CodeGen;
import com.github.ConcordiaSOEN341.Interfaces.ICodeGen;
import com.github.ConcordiaSOEN341.Interfaces.IParser;
import com.github.ConcordiaSOEN341.Lexer.Lexer;
import com.github.ConcordiaSOEN341.Parser.Parser;
import com.github.ConcordiaSOEN341.Reader.Reader;

public class Driver {
    public static void main(String[] args) {

        String fileName = "src/TestInherentMnemonics.asm";
        IParser parser = new Parser(new Lexer(new Reader(fileName)));

        ICodeGen cg = new CodeGen();
        cg.generateListingFile(fileName, parser.parse());
    }

}


