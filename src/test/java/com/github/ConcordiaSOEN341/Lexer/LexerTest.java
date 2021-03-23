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
public class LexerTest extends TestCase {

    private ArrayList<Character> file;
    private IReader rTest;
    private ILexer lTest;

    @Test
    public void lexer_EOF() {
        //Arrange
        file = new ArrayList<>();
        file.add('~');
        rTest = new ReaderMoq(file);
        lTest = new Lexer(rTest);

        ArrayList<Token> expectedTList = new ArrayList<>();
        expectedTList.add(new Token("", new Position(1, 0, 0), TokenType.EOF));

        //Act
        ArrayList<IToken> actualTList = lTest.generateTokenList();


        //Assert
        assertEquals(expectedTList.toString(), actualTList.toString());
    }

    @Test
    public void lexer_SpaceEOF() {
        //Arrange
        file = new ArrayList<>();
        file.add(' ');
        file.add('~');
        rTest = new ReaderMoq(file);
        lTest = new Lexer(rTest);

        ArrayList<Token> expectedTList = new ArrayList<>();
        expectedTList.add(new Token("", new Position(1, 1, 1), TokenType.EOF));

        //Act
        ArrayList<IToken> actualTList = lTest.generateTokenList();


        //Assert
        assertEquals(expectedTList.toString(), actualTList.toString());
    }

    @Test
    public void lexer_MnemonicEOF() {
        //Arrange
        file = new ArrayList<>();
        file.add('h');
        file.add('a');
        file.add('l');
        file.add('t');
        file.add('~');
        rTest = new ReaderMoq(file);
        lTest = new Lexer(rTest);

        ArrayList<Token> expectedTList = new ArrayList<>();
        expectedTList.add(new Token("halt", new Position(1, 0, 4), TokenType.MNEMONIC));
        expectedTList.add(new Token("", new Position(1, 4, 4), TokenType.EOF));

        //Act
        ArrayList<IToken> actualTList = lTest.generateTokenList();


        //Assert
        assertEquals(expectedTList.toString(), actualTList.toString());
    }

    @Test
    public void lexer_IdentifierEOF() {
        //Arrange
        file = new ArrayList<>();
        file.add('a');
        file.add('b');
        file.add('c');
        file.add('~');
        rTest = new ReaderMoq(file);
        lTest = new Lexer(rTest);

        ArrayList<Token> expectedTList = new ArrayList<>();
        expectedTList.add(new Token("abc", new Position(1, 0, 3), TokenType.IDENTIFIER));
        expectedTList.add(new Token("", new Position(1, 3, 3), TokenType.EOF));

        //Act
        ArrayList<IToken> actualTList = lTest.generateTokenList();


        //Assert
        assertEquals(expectedTList.toString(), actualTList.toString());
    }

    @Test
    public void lexer_MnemonicSpaceEOF() {
        //Arrange
        file = new ArrayList<>();
        file.add('h');
        file.add('a');
        file.add('l');
        file.add('t');
        file.add(' ');
        file.add('~');
        rTest = new ReaderMoq(file);
        lTest = new Lexer(rTest);

        ArrayList<Token> expectedTList = new ArrayList<>();
        expectedTList.add(new Token("halt", new Position(1, 0, 4), TokenType.MNEMONIC));
        expectedTList.add(new Token("", new Position(1, 5, 5), TokenType.EOF));

        //Act
        ArrayList<IToken> actualTList = lTest.generateTokenList();


        //Assert
        assertEquals(expectedTList.toString(), actualTList.toString());
    }

    @Test
    public void lexer_SpaceMnemonicEOF() {
        //Arrange
        file = new ArrayList<>();
        file.add(' ');
        file.add('h');
        file.add('a');
        file.add('l');
        file.add('t');
        file.add('~');
        rTest = new ReaderMoq(file);
        lTest = new Lexer(rTest);

        ArrayList<Token> expectedTList = new ArrayList<>();
        expectedTList.add(new Token("halt", new Position(1, 1, 5), TokenType.MNEMONIC));
        expectedTList.add(new Token("", new Position(1, 5, 5), TokenType.EOF));

        //Act
        ArrayList<IToken> actualTList = lTest.generateTokenList();


        //Assert
        assertEquals(expectedTList.toString(), actualTList.toString());
    }

    @Test
    public void lexer_MnemonicEOLEOF() {
        //Arrange
        file = new ArrayList<>();
        file.add('h');
        file.add('a');
        file.add('l');
        file.add('t');
        file.add('\n');
        file.add('~');
        rTest = new ReaderMoq(file);
        lTest = new Lexer(rTest);

        ArrayList<Token> expectedTList = new ArrayList<>();
        expectedTList.add(new Token("halt", new Position(1, 0, 4), TokenType.MNEMONIC));
        expectedTList.add(new Token("", new Position(1, 4, 4), TokenType.EOL));
        expectedTList.add(new Token("", new Position(2, 0, 0), TokenType.EOF));

        //Act
        ArrayList<IToken> actualTList = lTest.generateTokenList();


        //Assert
        assertEquals(expectedTList.toString(), actualTList.toString());
    }

    @Test
    public void lexer_MnemonicEOLMnemonicEOF() {
        //Arrange
        file = new ArrayList<>();
        file.add('h');
        file.add('a');
        file.add('l');
        file.add('t');
        file.add('\n');
        file.add('p');
        file.add('o');
        file.add('p');
        file.add('~');
        rTest = new ReaderMoq(file);
        lTest = new Lexer(rTest);

        ArrayList<Token> expectedTList = new ArrayList<>();
        expectedTList.add(new Token("halt", new Position(1, 0, 4), TokenType.MNEMONIC));
        expectedTList.add(new Token("", new Position(1, 4, 4), TokenType.EOL));
        expectedTList.add(new Token("pop", new Position(2, 0, 3), TokenType.MNEMONIC));
        expectedTList.add(new Token("", new Position(2, 3, 3), TokenType.EOF));

        //Act
        ArrayList<IToken> actualTList = lTest.generateTokenList();

        //Assert
        assertEquals(expectedTList.toString(), actualTList.toString());
    }

    @Test
    public void lexer_idk() {
        //Arrange
        file = new ArrayList<>();
        file.add('h');
        file.add('a');
        file.add('l');
        file.add('t');
        file.add('.');
        file.add('y');
        file.add('5');
        file.add('~');
        rTest = new ReaderMoq(file);
        lTest = new Lexer(rTest);

        ArrayList<Token> expectedTList = new ArrayList<>();
        expectedTList.add(new Token("halt.y5", new Position(1, 0, 7), TokenType.IDENTIFIER));
        expectedTList.add(new Token("", new Position(1, 7, 7), TokenType.EOF));

        //Act
        ArrayList<IToken> actualTList = lTest.generateTokenList();

        //Assert
        assertEquals(expectedTList.toString(), actualTList.toString());
    }

    @Test
    public void lexer_idk2() {
        //Arrange
        file = new ArrayList<>();
        file.add('5');
        file.add('5');
        file.add('5');
        file.add('5');
        file.add(' ');
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
        expectedTList.add(new Token("5555", new Position(1, 0, 4), TokenType.OFFSET));
        expectedTList.add(new Token(";hello", new Position(1, 5, 11), TokenType.COMMENT));
        expectedTList.add(new Token("", new Position(1, 11,11), TokenType.EOF));

        //Act
        ArrayList<IToken> actualTList = lTest.generateTokenList();

        //Assert
        assertEquals(expectedTList.toString(), actualTList.toString());
    }

    @Test
    public void lexer_LineTest() {
        //Arrange
        file = new ArrayList<>();
        String s = "\t enter.u5 0\t ; OK, number <u5> [0..31].\r\n~";
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

    @Test
    public void lexer_Error1() {
        //Arrange
        file = new ArrayList<>();
        file.add('$');
        file.add('~');
        rTest = new ReaderMoq(file);
        lTest = new Lexer(rTest);

        ArrayList<Token> expectedTList = new ArrayList<>();
        expectedTList.add(new Token("$", new Position(1, 0, 1), TokenType.ERROR));
        expectedTList.add(new Token("", new Position(1, 1,1), TokenType.EOF));
        //Act
        ArrayList<IToken> actualTList = lTest.generateTokenList();


        //Assert
        assertEquals(expectedTList.toString(), actualTList.toString());
    }
}