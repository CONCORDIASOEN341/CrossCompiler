package com.github.ConcordiaSOEN341.Logger;

import com.github.ConcordiaSOEN341.CommandHandle.CommandHandler;

public class LexerLogger extends Logger {
    String prefix = LoggerColor.ANSI_BLUE + "[LEXER] " + LoggerColor.ANSI_RESET;

    public LexerLogger(CommandHandler cmdH) {
        super(cmdH);
    }

    @Override
    public void log(String message) {
        super.log(prefix + message);
    }
}
