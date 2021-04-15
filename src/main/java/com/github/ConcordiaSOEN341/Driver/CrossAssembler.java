package com.github.ConcordiaSOEN341.Driver;

import com.github.ConcordiaSOEN341.CodeGen.CodeGen;
import com.github.ConcordiaSOEN341.CommandHandle.CommandHandle;
import com.github.ConcordiaSOEN341.Error.ErrorReporter;
import com.github.ConcordiaSOEN341.Interfaces.*;
import com.github.ConcordiaSOEN341.Lexer.LexerFSM;
import com.github.ConcordiaSOEN341.Lexer.Lexer;
import com.github.ConcordiaSOEN341.Parser.Parser;
import com.github.ConcordiaSOEN341.Parser.ParserFSM;
import com.github.ConcordiaSOEN341.Tables.SymbolTable;
import com.github.ConcordiaSOEN341.Reader.Reader;

public class CrossAssembler implements ICrossAssembler {
    public void assemble(String[] args){
        CommandHandle commandHandle = new CommandHandle(args);

        IReader reader = new Reader(commandHandle.getFile());
        SymbolTable symbolTable = new SymbolTable();
        LexerFSM lexerFSM = new LexerFSM(reader);
        IErrorReporter reporter = new ErrorReporter();
        ILexer lexer = new Lexer(symbolTable, lexerFSM, reader, reporter);
        ICodeGen generator = new CodeGen(symbolTable, reporter);

        ParserFSM parserFSM = new ParserFSM();
        IParser p = new Parser(parserFSM, lexer, generator, reporter);
        p.parse(commandHandle.getFile());

        commandHandle.delete();
    }
}
