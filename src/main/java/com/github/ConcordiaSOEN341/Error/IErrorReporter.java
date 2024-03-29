package com.github.ConcordiaSOEN341.Error;

import java.util.ArrayList;

public interface IErrorReporter {
    void record(IError e);

    String report(String fileName);

    boolean hasErrors();

    int getNumberOfErrors();

    void clearErrors();

    ArrayList<IError> getErrors();
}
