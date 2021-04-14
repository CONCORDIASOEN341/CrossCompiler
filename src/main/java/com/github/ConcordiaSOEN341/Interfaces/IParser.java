package com.github.ConcordiaSOEN341.Interfaces;

import java.util.ArrayList;

public interface IParser {
    void parse(String file);

    ArrayList<ILineStatement> generateIR();
}
