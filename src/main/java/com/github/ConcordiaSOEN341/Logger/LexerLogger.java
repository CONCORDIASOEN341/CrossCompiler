package com.github.ConcordiaSOEN341.Logger;

public class LexerLogger extends Logger {
    static String prefix = LoggerColor.ANSI_BLUE + "[LEXER] " + LoggerColor.ANSI_RESET;

    @Override
    public void log(String message) {
        super.log(prefix + message);
    }
}
