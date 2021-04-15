package com.github.ConcordiaSOEN341.Logger;

public class ErrorLogger extends Logger {
    static String prefix = LoggerColor.ANSI_RED + "[ERROR] " + LoggerColor.ANSI_RESET;

    @Override
    public void log(String message) {
        super.log(prefix + message);
    }
}
