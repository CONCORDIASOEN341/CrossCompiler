package com.github.ConcordiaSOEN341.Interfaces;

public interface IReader {
    int getEof();

    int read();

    void closeStream();
}
