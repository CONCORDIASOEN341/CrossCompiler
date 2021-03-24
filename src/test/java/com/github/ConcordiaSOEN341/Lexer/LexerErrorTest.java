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
    }

    @Test
    public void lexer_errorTest() {
        //Arrange
        file = new ArrayList<>();
        String s = "\"`hel\n$!lo\" $~";
        for(char c : s.toCharArray()){
            file.add(c);
        }

        rTest = new ReaderMoq(file);
        lTest = new Lexer(rTest);

        ArrayList<Token> expectedTList = new ArrayList<>();
        expectedTList.add(new Token("5555", new Position(1, 0, 4), TokenType.OFFSET));
        expectedTList.add(new Token(";hello", new Position(1, 5, 11), TokenType.COMMENT));
        expectedTList.add(new Token("", new Position(1, 11,11), TokenType.EOF));

        //Act
        ArrayList<IToken> actualTList = lTest.generateTokenList();

        //Assert
        assertEquals(expectedTList.toString(), actualTList.toString());
    }
}

