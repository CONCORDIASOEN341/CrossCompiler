package com.github.ConcordiaSOEN341.CrossAssembler;

import com.github.ConcordiaSOEN341.CodeGen.CodeGen;
import com.github.ConcordiaSOEN341.CommandHandler.CommandHandler;
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
        ParserFSM parserFSM = new ParserFSM(loggerFactory);

        IErrorReporter reporter = new ErrorReporter(loggerFactory);
        ILexer lexer = new Lexer(symbolTable, lexerFSM, reader, loggerFactory, reporter);
        ICodeGen generator = new CodeGen(symbolTable, loggerFactory, reporter);

        IParser p = new Parser(parserFSM, lexer, generator, loggerFactory, reporter);
        p.parse(commandHandler.getFile());
    }
}
