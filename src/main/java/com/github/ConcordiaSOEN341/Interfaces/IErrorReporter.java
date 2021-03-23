package com.github.ConcordiaSOEN341.Interfaces;

public interface IErrorReporter {
    void record(IError error);
    void report();
}
