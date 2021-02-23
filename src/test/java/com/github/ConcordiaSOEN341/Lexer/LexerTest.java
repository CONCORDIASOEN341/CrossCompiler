package com.github.ConcordiaSOEN341.Lexer;

import com.github.ConcordiaSOEN341.Reader.IReader;
import org.junit.Test;
import junit.framework.TestCase;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class LexerTest extends TestCase {

    /**
     *     PETER
     *     word <eof>
     *     NotMne <eof>
     *
     *     word <space> <eol><eof>
     *     word <space> <word><eof>
     *     word <eol><eof>
     *     word <eof>
     *
     *     ANDREI
     *     space <word><eof>
     *     space <eol><eof>
     *     space <eof>
     *
     *     DIMITRI
     *     <eof>
     *     <eol> <eof>
     */

    @Test
    public void checkHaltInstruction_expectCode00() {
        //Arrange
        ArrayList<Character> file = new ArrayList<>();
        file.add('h');
        file.add('a');
        file.add('l');
        file.add('t');
        file.add('~');

        IReader rTest = new ReaderTest(file);
        //Act
        ArrayList<Token> tokenTestList =  runLexer(rTest);


        //Assert
//        assertEquals(true,true);
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