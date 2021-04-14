package com.github.ConcordiaSOEN341.Interfaces;

import com.github.ConcordiaSOEN341.CommandHandle.CommandHandle;

public interface ILogger {
    default void log(String message){};
}
