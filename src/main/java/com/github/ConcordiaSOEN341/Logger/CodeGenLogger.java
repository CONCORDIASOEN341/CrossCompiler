package com.github.ConcordiaSOEN341.Logger;

import com.github.ConcordiaSOEN341.CrossAssembler.CommandHandler;

public class CodeGenLogger extends Logger {
    String prefix = LoggerColor.ANSI_PURPLE + "[CODEGEN] " + LoggerColor.ANSI_RESET;

    public CodeGenLogger(CommandHandler cmdH) {
        super(cmdH);
    }

    @Override
    public void log(String message) {
        super.log(prefix + message);
    }
}
