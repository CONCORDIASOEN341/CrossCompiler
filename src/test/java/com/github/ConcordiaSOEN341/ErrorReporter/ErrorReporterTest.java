package com.github.ConcordiaSOEN341.ErrorReporter;

import com.github.ConcordiaSOEN341.Error.Error;
import com.github.ConcordiaSOEN341.Error.ErrorReporter;
import com.github.ConcordiaSOEN341.Error.ErrorType;
import com.github.ConcordiaSOEN341.Interfaces.IError;
import com.github.ConcordiaSOEN341.Lexer.Position;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ErrorReporterTest extends TestCase {

    @Test
    public void ErrorReporter_RecordErrorAndReport() {
        //Arrange
        IError error = new Error(ErrorType.getDefault(), new Position(1, 1, 1));
        String file = "abc";
        String expectedReport = file + ":" + "Error:line " + "1" + ": " + "Invalid character.\n";

        //Act
        ErrorReporter.record(error);
        String report = ErrorReporter.report(file);

        //Assert
        assertEquals(expectedReport, report);
        assertEquals(1, ErrorReporter.getNumberOfErrors());

        ErrorReporter.clearErrors();
    }

    @Test
    public void ErrorReporter_HasErrors() {
        //Arrange
        IError error = new Error(ErrorType.getDefault(), new Position(1, 1, 1));
        String file = "abc";
        String expectedReport = file + ":" + "Error:line " + "1" + ": " + "Invalid character.";

        //Act
        ErrorReporter.record(error);
        boolean hasErrors = ErrorReporter.hasErrors();

        //Assert
        assertEquals(true, hasErrors);

        ErrorReporter.clearErrors();
    }

    @Test
    public void ErrorReporter_ClearErrors() {
        //Arrange
        IError error = new Error(ErrorType.getDefault(), new Position(1, 1, 1));
        String file = "abc";
        String expectedReport = file + ":" + "Error:line " + "1" + ": " + "Invalid character.";

        //Act
        ErrorReporter.record(error);
        ErrorReporter.clearErrors();

        //Assert
        assertEquals(false, ErrorReporter.hasErrors());

    }
}
