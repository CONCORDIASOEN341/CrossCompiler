package com.github.ConcordiaSOEN341.Logger;

import com.github.ConcordiaSOEN341.CrossAssembler.CommandHandler;

public class ErrorLogger extends Logger {
    String prefix = LoggerColor.ANSI_RED + "[ERROR] ";

    public ErrorLogger(CommandHandler cmdH) {
        super(cmdH);
    }

    @Override
    public void log(String message) {
        super.log(prefix + message + LoggerColor.ANSI_RESET);
    }
}
