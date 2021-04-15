package com.github.ConcordiaSOEN341.Logger;

import com.github.ConcordiaSOEN341.CommandHandler.CommandHandler;
import com.github.ConcordiaSOEN341.Interfaces.ILogger;

public class LoggerFactory {
    private final CommandHandler handler;

    public LoggerFactory(CommandHandler cmdH){
        handler = cmdH;
    }

    public ILogger getLogger(LoggerType loggerType) {
        ILogger logger;
        switch (loggerType) {
            case FILE -> logger = new FileLogger(handler);
            case ERROR -> logger = new ErrorLogger(handler);
            case LEXER -> logger = new LexerLogger(handler);
            case PARSER -> logger = new ParserLogger(handler);
            case CODEGEN -> logger = new CodeGenLogger(handler);
            case READER -> logger = new ReaderLogger(handler);
            default -> throw new IllegalStateException("Unexpected value: " + loggerType);
        }
        return logger;
    }
}
