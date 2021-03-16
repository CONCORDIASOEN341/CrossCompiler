package com.github.ConcordiaSOEN341.Interfaces;

import com.github.ConcordiaSOEN341.Parser.InstructionType;

import java.util.ArrayList;

public interface IParser {
    ArrayList<ILineStatement> parse();
    ArrayList<ILineStatement> generateIR(ArrayList<IToken> tList);
}
