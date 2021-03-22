package com.github.ConcordiaSOEN341.Lexer;

import com.github.ConcordiaSOEN341.Interfaces.ILexer;
import com.github.ConcordiaSOEN341.Interfaces.IReader;
import com.github.ConcordiaSOEN341.Interfaces.IToken;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class LexerTestComments extends TestCase {

    private ArrayList<Character> file;
    private IReader rTest;
    private ILexer lTest;

    @Test
    public void lexer_CommentNoWordsEOF() {
        //Arrange
        file = new ArrayList<>();
        file.add(';');
        file.add('~');
        rTest = new ReaderMoq(file);
        lTest = new Lexer(rTest);

        ArrayList<Token> expectedTList = new ArrayList<>();
        expectedTList.add(new Token(";", new Position(1, 0, 1), TokenType.COMMENT));
        expectedTList.add(new Token("", new Position(1, 1, 1), TokenType.EOF));

        //Act
        ArrayList<IToken> actualTList = lTest.generateTokenList();


        //Assert
        assertEquals(expectedTList.toString(), actualTList.toString());
    }

    @Test
    public void lexer_CommentEOF() {
        //Arrange
        file = new ArrayList<>();
        file.add(';');
        file.add('h');
        file.add('e');
        file.add('l');
        file.add('l');
        file.add('o');
        file.add('~');
        rTest = new ReaderMoq(file);
        lTest = new Lexer(rTest);

        ArrayList<Token> expectedTList = new ArrayList<>();
        expectedTList.add(new Token(";hello", new Position(1, 0, 6), TokenType.COMMENT));
        expectedTList.add(new Token("", new Position(1, 6, 6), TokenType.EOF));

        //Act
        ArrayList<IToken> actualTList = lTest.generateTokenList();

        //Assert
        assertEquals(expectedTList.toString(), actualTList.toString());
    }

    @Test
    public void lexer_CommentBackTrack() {
        //Arrange
        file = new ArrayList<>();
        file.add('h');
        file.add('e');
        file.add('l');
        file.add('l');
        file.add('o');
        file.add(';');
        file.add('~');
        rTest = new ReaderMoq(file);
        lTest = new Lexer(rTest);

        ArrayList<Token> expectedTList = new ArrayList<>();
        expectedTList.add(new Token("hello", new Position(1, 0, 5), TokenType.IDENTIFIER));
        expectedTList.add(new Token(";", new Position(1, 5, 6), TokenType.COMMENT));
        expectedTList.add(new Token("", new Position(1, 6, 6), TokenType.EOF));

        //Act
        ArrayList<IToken> actualTList = lTest.generateTokenList();


        //Assert
        assertEquals(expectedTList.toString(), actualTList.toString());
    }

    @Test
    public void lexer_CommentWithSpaceEOF() {
        //Arrange
        file = new ArrayList<>();
        file.add(';');
        file.add('h');
        file.add('e');
        file.add('l');
        file.add('l');
        file.add('o');
        file.add(' ');
        file.add('a');
        file.add('b');
        file.add('c');
        file.add('~');
        rTest = new ReaderMoq(file);
        lTest = new Lexer(rTest);

        ArrayList<Token> expectedTList = new ArrayList<>();
        expectedTList.add(new Token(";hello abc", new Position(1, 0, 10), TokenType.COMMENT));
        expectedTList.add(new Token("", new Position(1, 10, 10), TokenType.EOF));

        //Act
        ArrayList<IToken> actualTList = lTest.generateTokenList();


        //Assert
        assertEquals(expectedTList.toString(), actualTList.toString());
    }

    @Test
    public void lexer_CommentNewLine() {
        //Arrange
        file = new ArrayList<>();
        file.add(';');
        file.add('h');
        file.add('e');
        file.add('l');
        file.add('l');
        file.add('o');
        file.add(' ');
        file.add('a');
        file.add('b');
        file.add('c');
        file.add('~');
        rTest = new ReaderMoq(file);
        lTest = new Lexer(rTest);

        ArrayList<Token> expectedTList = new ArrayList<>();
        expectedTList.add(new Token(";hello abc", new Position(1, 0, 10), TokenType.COMMENT));
        expectedTList.add(new Token("", new Position(1, 10, 10), TokenType.EOF));

        //Act
        ArrayList<IToken> actualTList = lTest.generateTokenList();


        //Assert
        assertEquals(expectedTList.toString(), actualTList.toString());
    }

    @Test
    public void lexer_Directive() {
        //Arrange
        file = new ArrayList<>();
        file.add('"');
        file.add('"');
        file.add(';');
        file.add('h');
        file.add('e');
        file.add('l');
        file.add('l');
        file.add('o');
        file.add(' ');
        file.add('a');
        file.add('b');
        file.add('c');
        file.add('~');
        rTest = new ReaderMoq(file);
        lTest = new Lexer(rTest);

        ArrayList<Token> expectedTList = new ArrayList<>();
        expectedTList.add(new Token("\"\"", new Position(1, 0, 2), TokenType.CSTRING));
        expectedTList.add(new Token(";hello abc", new Position(1, 2, 12), TokenType.COMMENT));
        expectedTList.add(new Token("", new Position(1, 12, 12), TokenType.EOF));

        //Act
        ArrayList<IToken> actualTList = lTest.generateTokenList();


        //Assert
        assertEquals(expectedTList.toString(), actualTList.toString());
    }
}
