package com.github.ConcordiaSOEN341.Driver;

import com.github.ConcordiaSOEN341.CodeGen.CodeGen;
import com.github.ConcordiaSOEN341.Error.ErrorReporter;
import com.github.ConcordiaSOEN341.Interfaces.*;
import com.github.ConcordiaSOEN341.Lexer.DFA;
import com.github.ConcordiaSOEN341.Lexer.Lexer;
import com.github.ConcordiaSOEN341.Maps.SymbolTable;
import com.github.ConcordiaSOEN341.Parser.Parser;
import com.github.ConcordiaSOEN341.Reader.Reader;

public class CrossAssembler implements ICrossAssembler {
    public void assemble(String fileName){
        IReader reader = new Reader(fileName);
        SymbolTable symbolTable = new SymbolTable();
        DFA dfa = new DFA(reader);
        IErrorReporter reporter = new ErrorReporter();
        ILexer lexer = new Lexer(symbolTable, dfa, reader, reporter);

        IParser parser = new Parser(symbolTable, lexer, reporter);

        ICodeGen cg = new CodeGen();
        cg.generateListingFile(fileName, parser.parse(), reporter);
    }
}
