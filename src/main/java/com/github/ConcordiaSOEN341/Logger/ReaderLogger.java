package com.github.ConcordiaSOEN341.Logger;

import com.github.ConcordiaSOEN341.CrossAssembler.CommandHandler;

public class ReaderLogger extends Logger {
    String prefix = LoggerColor.ANSI_YELLOW + "[READER] " + LoggerColor.ANSI_RESET;

    public ReaderLogger(CommandHandler cmdH) {
        super(cmdH);
    }

    @Override
    public void log(String message) {
        super.log(prefix + message);
    }
}
