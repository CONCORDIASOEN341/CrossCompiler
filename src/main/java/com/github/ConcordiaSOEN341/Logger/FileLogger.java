package com.github.ConcordiaSOEN341.Logger;

public class FileLogger extends Logger {
    static String prefix = LoggerColor.ANSI_GREEN + "[FILE] " + LoggerColor.ANSI_RESET;

    @Override
    public void log(String message) {
        super.log(prefix + message);
    }
}
