package com.github.ConcordiaSOEN341.Lexer;

import com.github.ConcordiaSOEN341.Error.Error;
import com.github.ConcordiaSOEN341.Error.ErrorReporter;
import com.github.ConcordiaSOEN341.Error.ErrorType;
import com.github.ConcordiaSOEN341.Interfaces.*;
import com.github.ConcordiaSOEN341.Tables.SymbolTable;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class LexerTest extends TestCase {

    private ArrayList<Character> file;
    private ILexer lTest;
    private IErrorReporter eTest;

    private void init(ArrayList<Character> f){
        IReader rTest = new ReaderMoq(f);
        SymbolTable sTest = new SymbolTable();
        LexerFSM lexerFSMTest = new LexerFSM(rTest);
        eTest = new ErrorReporter();
        lTest = new Lexer(sTest, lexerFSMTest, rTest, eTest);
    }

    private ArrayList<IToken> generateTokenList(){
        IToken t;
        ArrayList<IToken> tokenList = new ArrayList<>();
        do {
            t = lTest.getNextToken();
            tokenList.add(t);
        } while(t.getTokenType() != TokenType.EOF);

        return tokenList;
    }

    @Test
    public void lexer_EOF() {
        //Arrange
        file = new ArrayList<>();
        file.add('~');
        init(file);

        ArrayList<Token> expectedTList = new ArrayList<>();
        expectedTList.add(new Token("", new Position(1, 0, 0), TokenType.EOF));

        //Act
        ArrayList<IToken> actualTList = generateTokenList();


        //Assert
        assertEquals(expectedTList.toString(), actualTList.toString());
    }

    @Test
    public void lexer_SpaceEOF() {
        //Arrange
        file = new ArrayList<>();
        file.add(' ');
        file.add('~');
        init(file);

        ArrayList<Token> expectedTList = new ArrayList<>();
        expectedTList.add(new Token("", new Position(1, 1, 1), TokenType.EOF));

        //Act
        ArrayList<IToken> actualTList = generateTokenList();


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
        init(file);

        ArrayList<Token> expectedTList = new ArrayList<>();
        expectedTList.add(new Token("halt", new Position(1, 0, 4), TokenType.MNEMONIC));
        expectedTList.add(new Token("", new Position(1, 4, 4), TokenType.EOF));

        //Act
        ArrayList<IToken> actualTList = generateTokenList();


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
        init(file);

        ArrayList<Token> expectedTList = new ArrayList<>();
        expectedTList.add(new Token("abc", new Position(1, 0, 3), TokenType.IDENTIFIER));
        expectedTList.add(new Token("", new Position(1, 3, 3), TokenType.EOF));

        //Act
        ArrayList<IToken> actualTList = generateTokenList();


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
        init(file);

        ArrayList<Token> expectedTList = new ArrayList<>();
        expectedTList.add(new Token("halt", new Position(1, 0, 4), TokenType.MNEMONIC));
        expectedTList.add(new Token("", new Position(1, 5, 5), TokenType.EOF));

        //Act
        ArrayList<IToken> actualTList = generateTokenList();


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
        init(file);

        ArrayList<Token> expectedTList = new ArrayList<>();
        expectedTList.add(new Token("halt", new Position(1, 1, 5), TokenType.MNEMONIC));
        expectedTList.add(new Token("", new Position(1, 5, 5), TokenType.EOF));

        //Act
        ArrayList<IToken> actualTList = generateTokenList();


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
        init(file);

        ArrayList<Token> expectedTList = new ArrayList<>();
        expectedTList.add(new Token("halt", new Position(1, 0, 4), TokenType.MNEMONIC));
        expectedTList.add(new Token("", new Position(1, 4, 4), TokenType.EOL));
        expectedTList.add(new Token("", new Position(2, 0, 0), TokenType.EOF));

        //Act
        ArrayList<IToken> actualTList = generateTokenList();


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
        init(file);

        ArrayList<Token> expectedTList = new ArrayList<>();
        expectedTList.add(new Token("halt", new Position(1, 0, 4), TokenType.MNEMONIC));
        expectedTList.add(new Token("", new Position(1, 4, 4), TokenType.EOL));
        expectedTList.add(new Token("pop", new Position(2, 0, 3), TokenType.MNEMONIC));
        expectedTList.add(new Token("", new Position(2, 3, 3), TokenType.EOF));

        //Act
        ArrayList<IToken> actualTList = generateTokenList();

        //Assert
        assertEquals(expectedTList.toString(), actualTList.toString());
    }

    @Test
    public void lexer_Offset() {
        //Arrange
        file = new ArrayList<>();
        file.add('5');
        file.add('5');
        file.add('5');
        file.add('5');
        file.add('~');
        init(file);

        ArrayList<Token> expectedTList = new ArrayList<>();
        expectedTList.add(new Token("5555", new Position(1, 0, 4), TokenType.OFFSET));
        expectedTList.add(new Token("", new Position(1, 4, 4), TokenType.EOF));

        //Act
        ArrayList<IToken> actualTList = generateTokenList();

        //Assert
        assertEquals(expectedTList.toString(), actualTList.toString());
    }

    @Test
    public void lexer_negativeNumberTest() {
        //Arrange
        file = new ArrayList<>();
        String s = "-5~";
        for (char c : s.toCharArray()) {
            file.add(c);
        }
        init(file);

        ArrayList<Token> expectedTList = new ArrayList<>();
        expectedTList.add(new Token("-5", new Position(1, 0, 2), TokenType.OFFSET));
        expectedTList.add(new Token("", new Position(1, 2, 2), TokenType.EOF));

        //Act
        ArrayList<IToken> actualTList = generateTokenList();

        //Assert
        assertEquals(expectedTList.toString(), actualTList.toString());
    }

    @Test
    public void lexer_CString() {
        //Arrange
        file = new ArrayList<>();
        String s = "\"Dmitri\"~";
        for (char c : s.toCharArray()) {
            file.add(c);
        }
        init(file);

        ArrayList<Token> expectedTList = new ArrayList<>();
        expectedTList.add(new Token("\"Dmitri\"", new Position(1, 0, 8), TokenType.CSTRING));
        expectedTList.add(new Token("", new Position(1, 8, 8), TokenType.EOF));

        //Act
        ArrayList<IToken> actualTList = generateTokenList();

        //Assert
        assertEquals(expectedTList.toString(), actualTList.toString());
    }

    @Test
    public void lexer_Directive() {
        //Arrange
        file = new ArrayList<>();
        String s = ".Dmitri~";
        for (char c : s.toCharArray()) {
            file.add(c);
        }
        init(file);

        ArrayList<Token> expectedTList = new ArrayList<>();
        expectedTList.add(new Token(".Dmitri", new Position(1, 0, 7), TokenType.DIRECTIVE));
        expectedTList.add(new Token("", new Position(1, 7, 7), TokenType.EOF));

        //Act
        ArrayList<IToken> actualTList = generateTokenList();

        //Assert
        assertEquals(expectedTList.toString(), actualTList.toString());
    }

    @Test
    public void lexer_LineTest() {
        //Arrange
        file = new ArrayList<>();
        String s = "\t enter.u5 0\t ; OK, number <u5> [0..31].\r\n~";
        for (char c : s.toCharArray()) {
            file.add(c);
        }
        init(file);

        ArrayList<Token> expectedTList = new ArrayList<>();
        expectedTList.add(new Token("enter.u5", new Position(1, 9, 17), TokenType.IDENTIFIER));
        expectedTList.add(new Token("0", new Position(1, 18, 19), TokenType.OFFSET));
        expectedTList.add(new Token("; OK, number <u5> [0..31].", new Position(1, 28, 54), TokenType.COMMENT));
        expectedTList.add(new Token("", new Position(1, 54, 54), TokenType.EOL));
        expectedTList.add(new Token("", new Position(2, 0, 0), TokenType.EOF));

        //Act
        ArrayList<IToken> actualTList = generateTokenList();

        //Assert
        assertEquals(expectedTList.toString(), actualTList.toString());
    }

    // COMMENTS BELOW

    @Test
    public void lexer_CommentNoWordsEOF() {
        //Arrange
        file = new ArrayList<>();
        file.add(';');
        file.add('~');
        init(file);

        ArrayList<Token> expectedTList = new ArrayList<>();
        expectedTList.add(new Token(";", new Position(1, 0, 1), TokenType.COMMENT));
        expectedTList.add(new Token("", new Position(1, 1, 1), TokenType.EOF));

        //Act
        ArrayList<IToken> actualTList = generateTokenList();


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
        init(file);

        ArrayList<Token> expectedTList = new ArrayList<>();
        expectedTList.add(new Token(";hello", new Position(1, 0, 6), TokenType.COMMENT));
        expectedTList.add(new Token("", new Position(1, 6, 6), TokenType.EOF));

        //Act
        ArrayList<IToken> actualTList = generateTokenList();

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
        init(file);

        ArrayList<Token> expectedTList = new ArrayList<>();
        expectedTList.add(new Token("hello", new Position(1, 0, 5), TokenType.IDENTIFIER));
        expectedTList.add(new Token(";", new Position(1, 5, 6), TokenType.COMMENT));
        expectedTList.add(new Token("", new Position(1, 6, 6), TokenType.EOF));

        //Act
        ArrayList<IToken> actualTList = generateTokenList();


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
        init(file);

        ArrayList<Token> expectedTList = new ArrayList<>();
        expectedTList.add(new Token(";hello abc", new Position(1, 0, 10), TokenType.COMMENT));
        expectedTList.add(new Token("", new Position(1, 10, 10), TokenType.EOF));

        //Act
        ArrayList<IToken> actualTList = generateTokenList();


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
        file.add('\n');
        file.add('a');
        file.add('b');
        file.add('c');
        file.add('~');
        init(file);

        ArrayList<Token> expectedTList = new ArrayList<>();
        expectedTList.add(new Token(";hello", new Position(1, 0, 6), TokenType.COMMENT));
        expectedTList.add(new Token("", new Position(1, 6, 6), TokenType.EOL));
        expectedTList.add(new Token("abc", new Position(2, 0, 3), TokenType.IDENTIFIER));
        expectedTList.add(new Token("", new Position(2, 3, 3), TokenType.EOF));

        //Act
        ArrayList<IToken> actualTList = generateTokenList();


        //Assert
        assertEquals(expectedTList.toString(), actualTList.toString());
    }

    @Test
    public void lexer_CommentWithInvalidCharacter() {
        //Arrange
        file = new ArrayList<>();
        file.add(';');
        file.add('$');
        file.add('~');
        init(file);

        ArrayList<Token> expectedTList = new ArrayList<>();
        expectedTList.add(new Token(";$", new Position(1, 0, 2), TokenType.COMMENT));
        expectedTList.add(new Token("", new Position(1, 2, 2), TokenType.EOF));

        //Act
        ArrayList<IToken> actualTList = generateTokenList();


        //Assert
        assertEquals(expectedTList.toString(), actualTList.toString());
    }

    // ERRORS BELOW

    @Test
    public void lexer_InvalidCharacter() {
        //Arrange
        file = new ArrayList<>();
        file.add('$');
        file.add('~');
        init(file);

        ArrayList<Token> expectedTList = new ArrayList<>();
        ArrayList<IError> expectedErrorsList = new ArrayList<>();

        expectedTList.add(new Token("$", new Position(1, 0, 1), TokenType.EOF));
        expectedErrorsList.add(new Error(ErrorType.INVALID_CHARACTER, new Position(1, 0, 1)));

        //System.out.println(expectedErrorsList);

        //Act
        ArrayList<IToken> actualTList = generateTokenList();

        //Assert
        assertEquals(expectedTList.toString(), actualTList.toString());
        assertEquals(expectedErrorsList.toString(), eTest.getErrors().toString());
    }

    @Test
    public void lexer_EOLInString() {
        //Arrange
        file = new ArrayList<>();
        file.add('\"');
        file.add('\n');
        file.add('~');
        init(file);

        ArrayList<Token> expectedTList = new ArrayList<>();
        ArrayList<IError> expectedErrorsList = new ArrayList<>();

        expectedTList.add(new Token("\"", new Position(1, 0, 1), TokenType.ERROR));
        expectedTList.add(new Token("", new Position(1, 1, 1), TokenType.EOL));
        expectedTList.add(new Token("", new Position(2, 0, 0), TokenType.EOF));
        expectedErrorsList.add(new Error(ErrorType.EOL_FOUND, new Position(1, 1, 2)));

        //System.out.println(expectedErrorsList);

        //Act
        ArrayList<IToken> actualTList = generateTokenList();

        //Assert
        assertEquals(expectedTList.toString(), actualTList.toString());
        assertEquals(expectedErrorsList.toString(), eTest.getErrors().toString());
    }

    @Test
    public void lexer_EOFInString() {
        //Arrange
        file = new ArrayList<>();
        file.add('\"');
        file.add('~');
        init(file);

        ArrayList<Token> expectedTList = new ArrayList<>();
        ArrayList<IError> expectedErrorsList = new ArrayList<>();

        expectedTList.add(new Token("\"", new Position(1, 0, 1), TokenType.ERROR));
        expectedTList.add(new Token("", new Position(1, 1, 1), TokenType.EOF));
        expectedErrorsList.add(new Error(ErrorType.EOF_FOUND, new Position(1, 1, 2)));

        //System.out.println(expectedErrorsList);

        //Act
        ArrayList<IToken> actualTList = generateTokenList();

        //Assert
        assertEquals(expectedTList.toString(), actualTList.toString());
        assertEquals(expectedErrorsList.toString(), eTest.getErrors().toString());
    }
}