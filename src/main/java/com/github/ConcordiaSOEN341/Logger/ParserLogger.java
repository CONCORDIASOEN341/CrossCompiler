package com.github.ConcordiaSOEN341.Logger;

import com.github.ConcordiaSOEN341.Interfaces.ILogger;

public class ParserLogger implements ILogger {
    static String prefix = LoggerColor.ANSI_WHITE + "[PARSER] " + LoggerColor.ANSI_RESET;

    public void log(String message) {
        System.out.println(prefix + message);
    }
}
