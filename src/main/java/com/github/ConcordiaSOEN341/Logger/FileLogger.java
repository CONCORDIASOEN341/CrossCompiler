package com.github.ConcordiaSOEN341.Logger;

import com.github.ConcordiaSOEN341.CrossAssembler.CommandHandler;

public class FileLogger extends Logger {
    String prefix = LoggerColor.ANSI_GREEN + "[FILE] " + LoggerColor.ANSI_RESET;

    public FileLogger(CommandHandler cmdH) {
        super(cmdH);
    }

    @Override
    public void log(String message) {
        super.log(prefix + message);
    }
}
