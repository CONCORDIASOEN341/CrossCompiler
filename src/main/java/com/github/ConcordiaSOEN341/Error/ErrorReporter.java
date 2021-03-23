package com.github.ConcordiaSOEN341.Error;

import com.github.ConcordiaSOEN341.Interfaces.IError;
import com.github.ConcordiaSOEN341.Interfaces.IErrorReporter;

import java.util.ArrayList;
import java.util.Comparator;

public class ErrorReporter implements IErrorReporter {
    private static final ArrayList<IError> errors = new ArrayList<>();
    private final Comparator<IError> ascLines = Comparator.comparingInt(e -> e.getPosition().getLine());

    public ErrorReporter(){}

    @Override
    public void record(IError error) {
        errors.add(error);
    }

    @Override
    public void report() {
        errors.sort(ascLines);

        for (IError e : errors){
            System.out.println(e);
        }

    }
}
