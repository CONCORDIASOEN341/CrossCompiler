package com.github.ConcordiaSOEN341.Logger;

public class ReaderLogger extends Logger {
    static String prefix = LoggerColor.ANSI_YELLOW + "[READER] " + LoggerColor.ANSI_RESET;

    @Override
    public void log(String message) {
        super.log(prefix + message);
    }
}
