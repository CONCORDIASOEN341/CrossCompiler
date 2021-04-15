package com.github.ConcordiaSOEN341.Driver;

import com.github.ConcordiaSOEN341.CodeGen.CodeGen;
import com.github.ConcordiaSOEN341.CommandHandle.CommandHandler;
import com.github.ConcordiaSOEN341.Error.ErrorReporter;
import com.github.ConcordiaSOEN341.Interfaces.*;
import com.github.ConcordiaSOEN341.Lexer.LexerFSM;
import com.github.ConcordiaSOEN341.Lexer.Lexer;
import com.github.ConcordiaSOEN341.Logger.LoggerFactory;
import com.github.ConcordiaSOEN341.Parser.Parser;
import com.github.ConcordiaSOEN341.Parser.ParserFSM;
import com.github.ConcordiaSOEN341.Tables.SymbolTable;
import com.github.ConcordiaSOEN341.Reader.Reader;


public class CrossAssembler implements ICrossAssembler {
    public void assemble(String[] args){
        CommandHandler commandHandler = new CommandHandler(args);
        LoggerFactory loggerFactory = new LoggerFactory(commandHandler);

        IReader reader = new Reader(commandHandler.getFile(), loggerFactory);
        SymbolTable symbolTable = new SymbolTable();
        LexerFSM lexerFSM = new LexerFSM(reader, loggerFactory);
        IErrorReporter reporter = new ErrorReporter(loggerFactory);
        ILexer lexer = new Lexer(symbolTable, lexerFSM, reader, reporter);
        ICodeGen generator = new CodeGen(symbolTable, loggerFactory, reporter);

        ParserFSM parserFSM = new ParserFSM();
        IParser p = new Parser(parserFSM, lexer, generator, reporter);
        p.parse(commandHandler.getFile());
    }
}
