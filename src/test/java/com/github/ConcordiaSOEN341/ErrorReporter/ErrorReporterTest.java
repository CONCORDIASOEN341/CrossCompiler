package com.github.ConcordiaSOEN341.ErrorReporter;

import com.github.ConcordiaSOEN341.CommandHandler.CommandHandler;
import com.github.ConcordiaSOEN341.Error.Error;
import com.github.ConcordiaSOEN341.Error.ErrorReporter;
import com.github.ConcordiaSOEN341.Error.ErrorType;
import com.github.ConcordiaSOEN341.Interfaces.IError;
import com.github.ConcordiaSOEN341.Interfaces.IErrorReporter;
import com.github.ConcordiaSOEN341.Lexer.Position;
import com.github.ConcordiaSOEN341.Logger.LoggerFactory;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ErrorReporterTest extends TestCase {
    private IErrorReporter eTest;

    private void init(){
        LoggerFactory lFTest = new LoggerFactory(new CommandHandler());
        eTest = new ErrorReporter(lFTest);
    }

    @Test
    public void ErrorReporter_RecordErrorAndReport() {
        //Arrange
        init();
        IError error = new Error(ErrorType.getDefault(), new Position(1, 1, 1));
        String file = "abc";
        String expectedReport = file + ":" + "Error:line " + "1" + ": " + "Invalid character.\n";

        //Act
        eTest.record(error);
        String report = eTest.report(file);

        //Assert
        assertEquals(expectedReport, report);
        assertEquals(1, eTest.getNumberOfErrors());

        eTest.clearErrors();
    }

    @Test
    public void ErrorReporter_HasErrors() {
        //Arrange
        init();
        IError error = new Error(ErrorType.getDefault(), new Position(1, 1, 1));
        String file = "abc";
        String expectedReport = file + ":" + "Error:line " + "1" + ": " + "Invalid character.";

        //Act
        eTest.record(error);
        boolean hasErrors = eTest.hasErrors();

        //Assert
        assertTrue(hasErrors);

        eTest.clearErrors();
    }

    @Test
    public void ErrorReporter_ClearErrors() {
        //Arrange
        init();
        IError error = new Error(ErrorType.getDefault(), new Position(1, 1, 1));
        String file = "abc";
        String expectedReport = file + ":" + "Error:line " + "1" + ": " + "Invalid character.";

        //Act
        eTest.record(error);
        eTest.clearErrors();

        //Assert
        assertFalse(eTest.hasErrors());

    }
}
