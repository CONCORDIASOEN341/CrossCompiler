package com.github.ConcordiaSOEN341.Driver;

import com.github.ConcordiaSOEN341.CodeGen.CodeGen;
import com.github.ConcordiaSOEN341.Interfaces.*;
import com.github.ConcordiaSOEN341.Lexer.Lexer;
import com.github.ConcordiaSOEN341.Parser.Parser;
import com.github.ConcordiaSOEN341.Reader.Reader;

import java.util.ArrayList;

public class Driver {
    public static void main(String[] args) {

        String fileName = "src/TestInherentMnemonics.asm";

        IReader reader = new Reader(fileName);
        ILexer lexer = new Lexer(reader);
        IParser parser = new Parser();
        ICodeGen cg = new CodeGen();

        ArrayList<IToken> tokenList = lexer.generateTokenList();

        ArrayList<ILineStatement> ir = parser.generateIR(tokenList);

        cg.generateListingFile(fileName,ir);
    }

}


