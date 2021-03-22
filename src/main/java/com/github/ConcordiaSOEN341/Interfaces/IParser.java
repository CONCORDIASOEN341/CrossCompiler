package com.github.ConcordiaSOEN341.Interfaces;

import java.util.ArrayList;

public interface IParser {
    ArrayList<ILineStatement> parse();

    @Deprecated
    ArrayList<ILineStatement> generateIR(ArrayList<IToken> tList);
}
