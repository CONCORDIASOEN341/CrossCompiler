package com.github.ConcordiaSOEN341.Lexer;

import com.github.ConcordiaSOEN341.Error.Error;
import com.github.ConcordiaSOEN341.Error.ErrorReporter;
import com.github.ConcordiaSOEN341.Error.ErrorType;
import com.github.ConcordiaSOEN341.Interfaces.IError;
import com.github.ConcordiaSOEN341.Interfaces.ILexer;
import com.github.ConcordiaSOEN341.Interfaces.IReader;
import com.github.ConcordiaSOEN341.Interfaces.IToken;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class LexerErrorTest extends TestCase {

    private ArrayList<Character> file;
    private IReader rTest;
    private ILexer lTest;

    @Test
    public void lexer_InvalidCharacter() {
        //Arrange
        file = new ArrayList<>();
        file.add('$');
        file.add('~');
        rTest = new ReaderMoq(file);
        lTest = new Lexer(rTest);

        ArrayList<Token> expectedTList = new ArrayList<>();
        ArrayList<IError> expectedErrorsList = new ArrayList<>();

        expectedTList.add(new Token("$", new Position(1, 0,1), TokenType.EOF));
        expectedErrorsList.add(new Error(ErrorType.INVALID_CHARACTER, new Position(1, 0,1)));

        //System.out.println(expectedErrorsList);

        //Act
        ArrayList<IToken> actualTList = lTest.generateTokenList();

        //Assert
        assertEquals(expectedTList.toString(), actualTList.toString());
        assertEquals(expectedErrorsList.toString(), ErrorReporter.getErrors().toString());

        ErrorReporter.clearErrors();
    }

    @Test
    public void lexer_EOLInString() {
        //Arrange
        file = new ArrayList<>();
        file.add('\"');
        file.add('\n');
        file.add('~');
        rTest = new ReaderMoq(file);
        lTest = new Lexer(rTest);

        ArrayList<Token> expectedTList = new ArrayList<>();
        ArrayList<IError> expectedErrorsList = new ArrayList<>();

        expectedTList.add(new Token("\"", new Position(1, 0, 1), TokenType.ERROR));
        expectedTList.add(new Token("", new Position(1, 1, 1), TokenType.EOL));
        expectedTList.add(new Token("", new Position(2, 0, 0), TokenType.EOF));
        expectedErrorsList.add(new Error(ErrorType.EOL_FOUND, new Position(1, 1,2)));

        //System.out.println(expectedErrorsList);

        //Act
        ArrayList<IToken> actualTList = lTest.generateTokenList();

        //Assert
        assertEquals(expectedTList.toString(), actualTList.toString());
        assertEquals(expectedErrorsList.toString(), ErrorReporter.getErrors().toString());

        ErrorReporter.clearErrors();
    }

    @Test
    public void lexer_EOFInString() {
        //Arrange
        file = new ArrayList<>();
        file.add('\"');
        file.add('~');
        rTest = new ReaderMoq(file);
        lTest = new Lexer(rTest);

        ArrayList<Token> expectedTList = new ArrayList<>();
        ArrayList<IError> expectedErrorsList = new ArrayList<>();

        expectedTList.add(new Token("\"", new Position(1, 0, 1), TokenType.ERROR));
        expectedTList.add(new Token("", new Position(1, 1, 1), TokenType.EOF));
        expectedErrorsList.add(new Error(ErrorType.EOF_FOUND, new Position(1, 1,2)));

        //System.out.println(expectedErrorsList);

        //Act
        ArrayList<IToken> actualTList = lTest.generateTokenList();

        //Assert
        assertEquals(expectedTList.toString(), actualTList.toString());
        assertEquals(expectedErrorsList.toString(), ErrorReporter.getErrors().toString());

        ErrorReporter.clearErrors();
    }
}

