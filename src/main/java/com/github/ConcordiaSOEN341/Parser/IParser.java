package com.github.ConcordiaSOEN341.Parser;

import com.github.ConcordiaSOEN341.Parser.ILineStatement;

import java.util.ArrayList;

public interface IParser {
    void parse(String file);

    ArrayList<ILineStatement> generateIR();
}
