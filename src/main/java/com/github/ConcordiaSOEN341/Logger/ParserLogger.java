package com.github.ConcordiaSOEN341.Logger;

public class ParserLogger extends Logger {


    static String prefix = LoggerColor.ANSI_GREEN+ "[PARSER] " + LoggerColor.ANSI_RESET;
    @Override
    public void log(String message) {
        super.log(prefix + message);
    }
}
