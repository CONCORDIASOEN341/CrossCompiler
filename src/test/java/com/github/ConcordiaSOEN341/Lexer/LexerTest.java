package com.github.ConcordiaSOEN341.Lexer;

import com.github.ConcordiaSOEN341.Interfaces.IReader;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class LexerTest extends TestCase {

    @Test
    public void lexer_EOF() {
        //Arrange
        ArrayList<Character> file = new ArrayList<>();
        file.add('~');

        IReader rTest = new ReaderTest(file);

        ArrayList<Token> expectedTList = new ArrayList<>();
        expectedTList.add(new Token("",1,0,0,TokenType.EOF));

        //Act
        ArrayList<Token> actualTList =  runLexer(rTest);


        //Assert
        assertEquals(expectedTList.toString(),actualTList.toString());
    }

    @Test
    public void lexer_SpaceEOF() {
        //Arrange
        ArrayList<Character> file = new ArrayList<>();
        file.add(' ');
        file.add('~');

        IReader rTest = new ReaderTest(file);

        ArrayList<Token> expectedTList = new ArrayList<>();
        expectedTList.add(new Token("",1,1,1,TokenType.EOF));

        //Act
        ArrayList<Token> actualTList =  runLexer(rTest);


        //Assert
        assertEquals(expectedTList.toString(),actualTList.toString());
    }

    @Test
    public void lexer_MnemonicEOF() {
        //Arrange
        ArrayList<Character> file = new ArrayList<>();
        file.add('h');
        file.add('a');
        file.add('l');
        file.add('t');
        file.add('~');

        IReader rTest = new ReaderTest(file);

        ArrayList<Token> expectedTList = new ArrayList<>();
        expectedTList.add(new Token("halt",1,0,4,TokenType.MNEMONIC));
        expectedTList.add(new Token("",1,4,4,TokenType.EOF));

        //Act
        ArrayList<Token> actualTList =  runLexer(rTest);


        //Assert
        assertEquals(expectedTList.toString(),actualTList.toString());
    }

    @Test
    public void lexer_IdentifierEOF() {
        //Arrange
        ArrayList<Character> file = new ArrayList<>();
        file.add('a');
        file.add('b');
        file.add('c');
        file.add('~');

        IReader rTest = new ReaderTest(file);

        ArrayList<Token> expectedTList = new ArrayList<>();
        expectedTList.add(new Token("abc",1,0,3,TokenType.IDENTIFIER));
        expectedTList.add(new Token("",1,3,3,TokenType.EOF));

        //Act
        ArrayList<Token> actualTList =  runLexer(rTest);


        //Assert
        assertEquals(expectedTList.toString(),actualTList.toString());
    }

    @Test
    public void lexer_MnemonicSpaceEOF() {
        //Arrange
        ArrayList<Character> file = new ArrayList<>();
        file.add('h');
        file.add('a');
        file.add('l');
        file.add('t');
        file.add(' ');
        file.add('~');

        IReader rTest = new ReaderTest(file);

        ArrayList<Token> expectedTList = new ArrayList<>();
        expectedTList.add(new Token("halt",1,0,4,TokenType.MNEMONIC));
        expectedTList.add(new Token("",1,5,5,TokenType.EOF));

        //Act
        ArrayList<Token> actualTList =  runLexer(rTest);


        //Assert
        assertEquals(expectedTList.toString(),actualTList.toString());
    }

    @Test
    public void lexer_SpaceMnemonicEOF() {
        //Arrange
        ArrayList<Character> file = new ArrayList<>();
        file.add(' ');
        file.add('h');
        file.add('a');
        file.add('l');
        file.add('t');
        file.add('~');

        IReader rTest = new ReaderTest(file);

        ArrayList<Token> expectedTList = new ArrayList<>();
        expectedTList.add(new Token("halt",1,1,5,TokenType.MNEMONIC));
        expectedTList.add(new Token("",1,5,5,TokenType.EOF));

        //Act
        ArrayList<Token> actualTList =  runLexer(rTest);


        //Assert
        assertEquals(expectedTList.toString(),actualTList.toString());
    }

    @Test
    public void lexer_MnemonicEOLEOF() {
        //Arrange
        ArrayList<Character> file = new ArrayList<>();
        file.add('h');
        file.add('a');
        file.add('l');
        file.add('t');
        file.add('\n');
        file.add('~');

        IReader rTest = new ReaderTest(file);

        ArrayList<Token> expectedTList = new ArrayList<>();
        expectedTList.add(new Token("halt",1,0,4,TokenType.MNEMONIC));
        expectedTList.add(new Token("",1,0,0,TokenType.EOL));
        expectedTList.add(new Token("",2,0,0,TokenType.EOF));

        //Act
        ArrayList<Token> actualTList =  runLexer(rTest);


        //Assert
        assertEquals(expectedTList.toString(),actualTList.toString());
    }

    @Test
    public void lexer_MnemonicEOLMnemonicEOF() {
        //Arrange
        ArrayList<Character> file = new ArrayList<>();
        file.add('h');
        file.add('a');
        file.add('l');
        file.add('t');
        file.add('\n');
        file.add('p');
        file.add('o');
        file.add('p');
        file.add('~');

        IReader rTest = new ReaderTest(file);

        ArrayList<Token> expectedTList = new ArrayList<>();
        expectedTList.add(new Token("halt",1,0,4,TokenType.MNEMONIC));
        expectedTList.add(new Token("",1,0,0,TokenType.EOL));
        expectedTList.add(new Token("pop",2,0,3,TokenType.MNEMONIC));
        expectedTList.add(new Token("",2,3,3,TokenType.EOF));

        //Act
        ArrayList<Token> actualTList =  runLexer(rTest);


        //Assert
        assertEquals(expectedTList.toString(),actualTList.toString());
    }

    private ArrayList<Token> runLexer(IReader r){
        Lexer lexer = new Lexer(r);
        ArrayList<Token> tokenList = new ArrayList<>(); // Use this for listing file/parsing
        Token t;

        do{
            t = lexer.getNextToken();

            tokenList.add(t);

        }while(t.getTokenType() != TokenType.EOF);

        return tokenList;
    }

}