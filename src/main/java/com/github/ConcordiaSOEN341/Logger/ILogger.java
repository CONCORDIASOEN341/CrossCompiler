package com.github.ConcordiaSOEN341.Logger;

import com.github.ConcordiaSOEN341.CrossAssembler.CommandHandler;

public interface ILogger {
    void log(String message);

    CommandHandler getHandler();
}
