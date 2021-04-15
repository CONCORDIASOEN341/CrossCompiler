package com.github.ConcordiaSOEN341.Error;

import com.github.ConcordiaSOEN341.Interfaces.IError;
import com.github.ConcordiaSOEN341.Interfaces.IErrorReporter;
import com.github.ConcordiaSOEN341.Interfaces.ILogger;
import com.github.ConcordiaSOEN341.Logger.LoggerFactory;
import com.github.ConcordiaSOEN341.Logger.LoggerType;

import java.util.ArrayList;
import java.util.Comparator;

public class ErrorReporter implements IErrorReporter {
    private final ArrayList<IError> errors = new ArrayList<>();
    private final Comparator<IError> ascLines = Comparator.comparingInt(e -> e.getPosition().getLine());

    private final ILogger logger = LoggerFactory.getLogger(LoggerType.ERROR);

    public void record(IError error) {
        logger.log("Error Recorded: " + error.toString());
        errors.add(error);
    }

    public String report(String fileName) {
        logger.log("Error Report Generated: ");
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

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public int getNumberOfErrors() {
        return errors.size();
    }

    public void clearErrors(){
        errors.clear();
    }

    public ArrayList<IError> getErrors() { return errors; }

}
