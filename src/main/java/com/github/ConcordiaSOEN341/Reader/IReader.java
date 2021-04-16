package com.github.ConcordiaSOEN341.Reader;

public interface IReader {
    int getEof();

    int read();

    void closeStream();
}
