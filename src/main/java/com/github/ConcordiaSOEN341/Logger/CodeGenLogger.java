package com.github.ConcordiaSOEN341.Logger;

public class CodeGenLogger extends Logger {
    static String prefix = LoggerColor.ANSI_PURPLE + "[CODEGEN] " + LoggerColor.ANSI_RESET;

    @Override
    public void log(String message) {
        super.log(prefix + message);
    }
}
