package com.github.ConcordiaSOEN341.Parser;


import com.github.ConcordiaSOEN341.CodeGen.SymbolTable;
import com.github.ConcordiaSOEN341.CrossAssembler.CommandHandler;
import com.github.ConcordiaSOEN341.Error.Error;
import com.github.ConcordiaSOEN341.Error.*;
import com.github.ConcordiaSOEN341.Lexer.*;
import com.github.ConcordiaSOEN341.Logger.LoggerFactory;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

//@RunWith(MockitoJUnitRunner.class)
public class ParserErrorTest  {

    private ArrayList<IToken> tokenList;
    private IParser pTest;
    private IErrorReporter eTest;

    private void init(ArrayList<IToken> input){
        LoggerFactory lFTest = new LoggerFactory(new CommandHandler());
        SymbolTable sTest = new SymbolTable();
        eTest = new ErrorReporter(lFTest);
        ParserFSM pFSMTest = new ParserFSM(lFTest);
        ILexer lTest = new LexerMoqForParser(input);
        pTest = new Parser(pFSMTest, lTest, sTest, lFTest, eTest);
    }

    @Test
    public void extraOperandWhenInherentInstruction(){
        //Arrange
        tokenList = new ArrayList<>();
        tokenList.add(new Token("pop", new Position(1,1,1), TokenType.MNEMONIC));
        tokenList.add(new Token("10", new Position(1,2,2), TokenType.OFFSET));
        tokenList.add(new Token("", new Position(1,3,3), TokenType.EOL));
        tokenList.add(new Token("", new Position(1,4,4), TokenType.EOF));
        init(tokenList);

        ArrayList<IError> errors = new ArrayList<>();
        IError error = new Error(ErrorType.EXTRA_OPERAND, new Position(1, 2, 2));
        errors.add(error);

        //Act
        ArrayList<ILineStatement> lineStatements = pTest.generateIR();

        //Assert
        assertEquals(errors.toString(), eTest.getErrors().toString());

        eTest.clearErrors();

    }

    @Test
    public void noOperandWhenImmediateOrRelativeInstruction(){
        //Arrange
        tokenList = new ArrayList<>();
        tokenList.add(new Token("enter.u5", new Position(1,1,1), TokenType.MNEMONIC));
        tokenList.add(new Token("", new Position(1,2,2), TokenType.EOL));
        tokenList.add(new Token("", new Position(1,3,3), TokenType.EOF));
        init(tokenList);

        ArrayList<IError> errors = new ArrayList<>();
        IError error = new Error(ErrorType.MISSING_OPERAND, new Position(1, 2, 2));
        errors.add(error);

        //Act
        ArrayList<ILineStatement> lineStatements = pTest.generateIR();

        //Assert
        assertEquals(errors.toString(), eTest.getErrors().toString());

        eTest.clearErrors();

    }

    @Test
    public void unsignedBitSpaceNotInInterval(){
        //Arrange
        tokenList = new ArrayList<>();
        tokenList.add(new Token("enter.u5", new Position(1,1,1), TokenType.MNEMONIC));
        tokenList.add(new Token("-1", new Position(1,1,1), TokenType.OFFSET));
        tokenList.add(new Token("", new Position(1,2,2), TokenType.EOL));
        tokenList.add(new Token("", new Position(1,3,3), TokenType.EOF));
        init(tokenList);

        ArrayList<IError> errors = new ArrayList<>();
        IError error = new Error(ErrorType.INVALID_UNSIGNED_5BIT_OPERAND, new Position(1, 2, 2));
        errors.add(error);

        //Act
        ArrayList<ILineStatement> lineStatements = pTest.generateIR();

        //Assert
        assertEquals(errors.toString(), eTest.getErrors().toString());

        eTest.clearErrors();

    }

    @Test
    public void signedBitSpaceNotInInterval(){
        //Arrange
        tokenList = new ArrayList<>();
        tokenList.add(new Token("ldc.i3", new Position(1,1,1), TokenType.MNEMONIC));
        tokenList.add(new Token("-5", new Position(1,1,1), TokenType.OFFSET));
        tokenList.add(new Token("", new Position(1,2,2), TokenType.EOL));
        tokenList.add(new Token("", new Position(1,3,3), TokenType.EOF));
        init(tokenList);

        ArrayList<IError> errors = new ArrayList<>();
        IError error = new Error(ErrorType.INVALID_SIGNED_3BIT_OPERAND, new Position(1, 2, 2));
        errors.add(error);

        //Act
        ArrayList<ILineStatement> lineStatements = pTest.generateIR();

        //Assert
        assertEquals(errors.toString(), eTest.getErrors().toString());

        eTest.clearErrors();
    }

    @Test
    public void parse_giveValid_expectNoError(){
        tokenList = new ArrayList<>();
        tokenList.add(new Token("add.i3", new Position(1, 0, 4), TokenType.MNEMONIC));
        tokenList.add(new Token("2", new Position(1, 0, 4), TokenType.OFFSET));
        tokenList.add(new Token("", new Position(1, 4, 4), TokenType.EOL));
        tokenList.add(new Token("", new Position(1, 3, 3), TokenType.EOF));

        init(tokenList);
        ArrayList<ILineStatement> lineStatements = pTest.generateIR();

        assertEquals(0, eTest.getNumberOfErrors());

    }
}

