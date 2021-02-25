package com.github.ConcordiaSOEN341.Interfaces;

import com.github.ConcordiaSOEN341.Lexer.Token;
import com.github.ConcordiaSOEN341.Parser.LineStatement;

import java.util.ArrayList;

public interface IParser {
    ArrayList<ILineStatement> generateIR(ArrayList<IToken> tList);
}
