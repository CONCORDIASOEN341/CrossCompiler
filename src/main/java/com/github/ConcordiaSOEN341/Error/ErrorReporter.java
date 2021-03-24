package com.github.ConcordiaSOEN341.Error;

import com.github.ConcordiaSOEN341.Interfaces.IError;

import java.util.ArrayList;
import java.util.Comparator;

public class ErrorReporter {
    private static final ArrayList<IError> errors = new ArrayList<>();
    private static final Comparator<IError> ascLines = Comparator.comparingInt(e -> e.getPosition().getLine());

    public ErrorReporter(){}

    public static void record(IError error) {
        errors.add(error);
    }

    public static void report() {
        errors.sort(ascLines);

        for (IError e : errors){
            System.out.println(e);
        }
    }

    public static boolean hasErrors(){
        return errors.size() > 0;
    }

    public static int getNumberOfErrors(){
        return errors.size();
    }
}
