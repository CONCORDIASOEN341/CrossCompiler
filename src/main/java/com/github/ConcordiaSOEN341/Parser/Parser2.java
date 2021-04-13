package com.github.ConcordiaSOEN341.Parser;

import com.github.ConcordiaSOEN341.Interfaces.IErrorReporter;
import com.github.ConcordiaSOEN341.Interfaces.ILexer;
import com.github.ConcordiaSOEN341.Interfaces.ILineStatement;
import com.github.ConcordiaSOEN341.Interfaces.IToken;
import com.github.ConcordiaSOEN341.Lexer.TokenType;
import com.github.ConcordiaSOEN341.Tables.SymbolTable;

import java.util.ArrayList;

public class Parser2 {
    private final ArrayList<ILineStatement> intermediateRep;
    private final ILexer lexer;
    private final SymbolTable symbolTable;
    private final IErrorReporter reporter;

    public Parser2(SymbolTable s, ILexer l, IErrorReporter e) {
        symbolTable = s;
        lexer = l;
        reporter = e;
        intermediateRep = new ArrayList<>();
    }

    public ArrayList<ILineStatement> parse() {
        int line = -1;
        Instruction instruction = null;
        LineStatement lStatement = null;
        IToken t;

        do {
            t = lexer.getNextToken();

        } while (t.getTokenType() != TokenType.EOF);
    }
}
