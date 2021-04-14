package com.github.ConcordiaSOEN341.Logger;

import com.github.ConcordiaSOEN341.CommandHandle.CommandHandle;
import com.github.ConcordiaSOEN341.Interfaces.ILogger;

public class Logger implements ILogger {
    public void log(String message) {
        if ( CommandHandle.getInstance() != null && CommandHandle.getInstance().isListing()) {
            System.out.println(message);
        }
    }
}
