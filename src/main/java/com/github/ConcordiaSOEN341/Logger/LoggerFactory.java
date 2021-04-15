package com.github.ConcordiaSOEN341.Logger;

import com.github.ConcordiaSOEN341.Interfaces.ILogger;

public class LoggerFactory {

    public static ILogger getLogger(LoggerType loggerType) {
        ILogger logger;
        switch (loggerType) {
            case FILE -> logger = new FileLogger();
            case ERROR -> logger = new ErrorLogger();
            case LEXER -> logger = new LexerLogger();
            case PARSER -> logger = new ParserLogger();
            case CODEGEN -> logger = new CodeGenLogger();
            case READER -> logger = new ReaderLogger();
            default -> throw new IllegalStateException("Unexpected value: " + loggerType);
        }
        return logger;
    }
}
