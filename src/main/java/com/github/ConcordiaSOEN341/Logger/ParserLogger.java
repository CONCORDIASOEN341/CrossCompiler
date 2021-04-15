package com.github.ConcordiaSOEN341.Logger;

import com.github.ConcordiaSOEN341.CommandHandle.CommandHandler;

public class ParserLogger extends Logger {
    String prefix = LoggerColor.ANSI_GREEN + "[PARSER] " + LoggerColor.ANSI_RESET;

    public ParserLogger(CommandHandler cmdH) {
        super(cmdH);
    }

    @Override
    public void log(String message) {
        super.log(prefix + message);
    }
}
