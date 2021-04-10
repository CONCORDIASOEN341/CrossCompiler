package com.github.ConcordiaSOEN341.Driver;

import com.github.ConcordiaSOEN341.CodeGen.CodeGen;
import com.github.ConcordiaSOEN341.Error.ErrorReporter;
import com.github.ConcordiaSOEN341.Interfaces.*;
import com.github.ConcordiaSOEN341.Lexer.LexerFSM;
import com.github.ConcordiaSOEN341.Lexer.Lexer;
import com.github.ConcordiaSOEN341.Tables.SymbolTable;
import com.github.ConcordiaSOEN341.Parser.Parser;
import com.github.ConcordiaSOEN341.Reader.Reader;

public class CrossAssembler implements ICrossAssembler {
    public void assemble(String fileName){
        IReader reader = new Reader(fileName);
        SymbolTable symbolTable = new SymbolTable();
        LexerFSM lexerFSM = new LexerFSM(reader);
        IErrorReporter reporter = new ErrorReporter();
        ILexer lexer = new Lexer(symbolTable, lexerFSM, reader, reporter);

        IParser parser = new Parser(symbolTable, lexer, reporter);

        ICodeGen cg = new CodeGen(symbolTable);
        cg.generateListingFile(fileName, parser.parse(), reporter);
    }
}
