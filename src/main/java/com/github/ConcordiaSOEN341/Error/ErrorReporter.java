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

    public static String report(String fileName) {
        if (fileName.contains("/")) {
            fileName = fileName.substring(fileName.lastIndexOf("/")+1);
        }
        errors.sort(ascLines);
        StringBuilder output = new StringBuilder();
        for (IError e : errors) {
            output.append(fileName).append(":").append(e).append("\n");
        }
        return output.toString();
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

    public static ArrayList<IError> getErrors() { return errors; }

}
