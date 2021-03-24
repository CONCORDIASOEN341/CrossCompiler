package com.github.ConcordiaSOEN341.Error;

import com.github.ConcordiaSOEN341.Interfaces.IError;

import java.util.ArrayList;
import java.util.Comparator;

public class ErrorReporter {
    private static final ArrayList<IError> errors = new ArrayList<>();
    private static final Comparator<IError> ascLines = Comparator.comparingInt(e -> e.getPosition().getLine());

    public static void record(IError error) {
        errors.add(error);
    }

    public static void report(String fileName) {
        errors.sort(ascLines);

        for (IError e : errors) {
            System.out.println(fileName + ":" + e);
        }
    }

    public static boolean hasErrors() {
        return !errors.isEmpty();
    }

    public static int getNumberOfErrors() {
        return errors.size();
    }

    public static void clearErrors(){
        errors.clear();
    }
}
