package com.github.ConcordiaSOEN341.Logger;

import com.github.ConcordiaSOEN341.CommandHandler.CommandHandler;
import com.github.ConcordiaSOEN341.Interfaces.ILogger;

public class Logger implements ILogger {
    private final CommandHandler handler;

    public Logger(CommandHandler cmdH) {
        handler = cmdH;
    }

    public void log(String message) {
        if (handler.isVerbose()) {
            System.out.println(message);
        }
    }
}
