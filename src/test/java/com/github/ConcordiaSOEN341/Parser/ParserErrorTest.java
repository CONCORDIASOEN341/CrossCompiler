package com.github.ConcordiaSOEN341.Parser;


import com.github.ConcordiaSOEN341.CrossAssembler.CommandHandler;
import com.github.ConcordiaSOEN341.Error.Error;
import com.github.ConcordiaSOEN341.Error.*;
import com.github.ConcordiaSOEN341.Lexer.*;
import com.github.ConcordiaSOEN341.Logger.LoggerFactory;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ParserErrorTest {

    private ArrayList<IToken> tokenList;
    private IParser pTest;
    private IErrorReporter eTest;
    private ArrayList<ILineStatement> irTest;

    private void init(ArrayList<IToken> input) {
        LoggerFactory lFTest = new LoggerFactory(new CommandHandler());
        SymbolTable sTest = new SymbolTable();
        eTest = new ErrorReporter(lFTest);
        ParserFSM pFSMTest = new ParserFSM(lFTest);
        ILexer lTest = new LexerMoqForParser(input);
        pTest = new Parser(pFSMTest, lTest, sTest, lFTest, eTest);
    }

    private void initOpCodeTable(ArrayList<ILineStatement> ir) {
        LoggerFactory lFTest = new LoggerFactory(new CommandHandler());
        SymbolTable sTest = new SymbolTable();
        eTest = new ErrorReporter(lFTest);
        ParserFSM pFSMTest = new ParserFSM(lFTest);
        pTest = new Parser(pFSMTest, sTest, lFTest, eTest);
        pTest.setIR(ir);
    }

    @Test
    public void generateIR_whenExtraOperandInherentInstruction_expectError() {
        //Arrange
        tokenList = new ArrayList<>();
        tokenList.add(new Token("pop", new Position(1, 1, 1), TokenType.MNEMONIC));
        tokenList.add(new Token("10", new Position(1, 2, 2), TokenType.OFFSET));
        tokenList.add(new Token("", new Position(1, 3, 3), TokenType.EOL));
        tokenList.add(new Token("", new Position(1, 4, 4), TokenType.EOF));
        init(tokenList);

        ArrayList<IError> errors = new ArrayList<>();
        IError error = new Error(ErrorType.EXTRA_OPERAND, new Position(1, 2, 2));
        errors.add(error);

        //Act
        ArrayList<ILineStatement> lineStatements = pTest.generateIR();

        //Assert
        assertEquals(errors.toString(), eTest.getErrors().toString());


    }

    @Test
    public void generateIR_whenNoOperandAndImmediateOrRelativeInstruction_expectError() {
        //Arrange
        tokenList = new ArrayList<>();
        tokenList.add(new Token("enter.u5", new Position(1, 1, 1), TokenType.MNEMONIC));
        tokenList.add(new Token("", new Position(1, 2, 2), TokenType.EOL));
        tokenList.add(new Token("", new Position(1, 3, 3), TokenType.EOF));
        init(tokenList);

        ArrayList<IError> errors = new ArrayList<>();
        IError error = new Error(ErrorType.MISSING_OPERAND, new Position(1, 2, 2));
        errors.add(error);

        //Act
        ArrayList<ILineStatement> lineStatements = pTest.generateIR();

        //Assert
        assertEquals(errors.toString(), eTest.getErrors().toString());


    }

    @Test
    public void generateIR_whenInvalidUnsignedBitSpace_expectError() {
        //Arrange
        tokenList = new ArrayList<>();
        tokenList.add(new Token("enter.u16", new Position(1, 1, 1), TokenType.MNEMONIC));
        tokenList.add(new Token("67000", new Position(1, 1, 1), TokenType.OFFSET));
        tokenList.add(new Token("", new Position(1, 2, 2), TokenType.EOL));
        tokenList.add(new Token("", new Position(1, 3, 3), TokenType.EOF));
        init(tokenList);

        ArrayList<IError> errors = new ArrayList<>();
        IError error = new Error(ErrorType.INVALID_UNSIGNED_16BIT_OPERAND, new Position(1, 2, 2));
        errors.add(error);

        //Act
        ArrayList<ILineStatement> lineStatements = pTest.generateIR();

        //Assert
        assertEquals(errors.toString(), eTest.getErrors().toString());


    }

    @Test
    public void generateIR_whenInvalidSignedBitSpace_expectError() {
        //Arrange
        tokenList = new ArrayList<>();
        tokenList.add(new Token("ldc.i3", new Position(1, 1, 1), TokenType.MNEMONIC));
        tokenList.add(new Token("-5", new Position(1, 1, 1), TokenType.OFFSET));
        tokenList.add(new Token("", new Position(1, 2, 2), TokenType.EOL));
        tokenList.add(new Token("", new Position(1, 3, 3), TokenType.EOF));
        init(tokenList);

        ArrayList<IError> errors = new ArrayList<>();
        IError error = new Error(ErrorType.INVALID_SIGNED_3BIT_OPERAND, new Position(1, 2, 2));
        errors.add(error);

        //Act
        ArrayList<ILineStatement> lineStatements = pTest.generateIR();

        //Assert
        assertEquals(errors.toString(), eTest.getErrors().toString());


    }

    @Test
    public void generateIR_giveValid_expectNoError() {
        tokenList = new ArrayList<>();
        tokenList.add(new Token("add.i3", new Position(1, 0, 4), TokenType.MNEMONIC));
        tokenList.add(new Token("2", new Position(1, 0, 4), TokenType.OFFSET));
        tokenList.add(new Token("", new Position(1, 4, 4), TokenType.EOL));
        tokenList.add(new Token("", new Position(1, 3, 3), TokenType.EOF));

        init(tokenList);
        ArrayList<ILineStatement> lineStatements = pTest.generateIR();

        assertEquals(0, eTest.getNumberOfErrors());

    }

    @Test
    public void generateOpCodeTable_whenLabelNotFound_With_Relative_Instruction_expectError() {
        //Arrange
        irTest = new ArrayList<>();
        irTest.add(new LineStatement(new Instruction(new Token("lda.i16"), new Token("Msg1"), InstructionType.RELATIVE)));

        initOpCodeTable(irTest);

        ArrayList<IError> errors = new ArrayList<>();
        errors.add(new Error("Msg1", ErrorType.LABEL_NOT_FOUND, new Position(1, 0, 0)));


        //Act
        ArrayList<IOpCodeTableElement> actualOpTable = pTest.generateOpCodeTable();

        //Assert
        assertEquals(errors.toString(), eTest.getErrors().toString());


    }

    @Test
    public void generateOpCodeTable_whenInvalidOperandLabelNotUsed_With_Immediate_Instruction_expectError() {
        //Arrange
        irTest = new ArrayList<>();
        irTest.add(new LineStatement(new Instruction(new Token("br.i5"), new Token("1"), InstructionType.IMMEDIATE)));
        initOpCodeTable(irTest);

        ArrayList<IError> errors = new ArrayList<>();
        errors.add(new Error(ErrorType.INVALID_OPERAND_LABEL_NOT_USED, new Position(1, 0, 0)));

        //Act
        ArrayList<IOpCodeTableElement> actualOpTable = pTest.generateOpCodeTable();

        //Assert
        assertEquals(errors.toString(), eTest.getErrors().toString());


    }

    @Test
    public void generateOpCodeTable_whenLabelDefinedError_expectError() {
        //Arrange
        irTest = new ArrayList<>();
        irTest.add(new LineStatement(new Instruction(new Token("lda.i16"), new Token("Msg1"), InstructionType.RELATIVE)));
        irTest.add(new LineStatement(new Token("Msg1"), new Instruction(new Token("ldc.i3"), new Token("2"), InstructionType.IMMEDIATE)));
        irTest.add(new LineStatement(new Token("Msg1"), new Instruction(new Token("ldc.i3"), new Token("3"), InstructionType.IMMEDIATE)));

        initOpCodeTable(irTest);

        ArrayList<IError> errors = new ArrayList<>();
        IError error = new Error("Msg1", ErrorType.LABEL_DEFINED, new Position(3, 0, 0));
        errors.add(error);

        //Act
        ArrayList<IOpCodeTableElement> actualOpTable = pTest.generateOpCodeTable();

        //Assert
        assertEquals(errors.toString(), eTest.getErrors().toString());


    }

    @Test
    public void generateOpCodeTable_whenInvalidOperandLabelNotUsed_With_Relative_Instruction_expectError() {
        //Arrange
        irTest = new ArrayList<>();
        irTest.add(new LineStatement(new Instruction(new Token("lda.i16"), new Token("1"), InstructionType.RELATIVE)));

        initOpCodeTable(irTest);

        ArrayList<IError> errors = new ArrayList<>();
        errors.add(new Error(ErrorType.INVALID_OPERAND_LABEL_NOT_USED, new Position(1, 0, 0)));

        //Act
        ArrayList<IOpCodeTableElement> actualOpTable = pTest.generateOpCodeTable();

        //Assert
        assertEquals(errors.toString(), eTest.getErrors().toString());


    }

    @Test
    public void generateIR_whenInvalidDirective_expectError() {
        //Arrange
        tokenList = new ArrayList<>();
        tokenList.add(new Token(".c", new Position(1, 0, 2), TokenType.DIRECTIVE));
        tokenList.add(new Token("hello", new Position(1, 3, 9), TokenType.CSTRING));
        tokenList.add(new Token("", new Position(1, 3, 3), TokenType.EOL));
        tokenList.add(new Token("", new Position(1, 4, 4), TokenType.EOF));
        init(tokenList);

        ArrayList<IError> errors = new ArrayList<>();
        errors.add(new Error(".c", ErrorType.INVALID_DIRECTIVE, new Position(1, 0, 2)));

        //Act
        ArrayList<ILineStatement> lineStatements = pTest.generateIR();

        //Assert
        assertEquals(errors.toString(), eTest.getErrors().toString());


    }

    @Test
    public void generateIR_whenCstringNotFound_expectError() {
        //Arrange
        tokenList = new ArrayList<>();
        tokenList.add(new Token(".cstring", new Position(1, 0, 6), TokenType.DIRECTIVE));
        tokenList.add(new Token("", new Position(1, 7, 7), TokenType.EOL));
        tokenList.add(new Token("", new Position(2, 0, 0), TokenType.EOF));
        init(tokenList);

        ArrayList<IError> errors = new ArrayList<>();
        errors.add(new Error(ErrorType.CSTRING_NOT_FOUND, new Position(1, 7, 7)));

        //Act
        ArrayList<ILineStatement> lineStatements = pTest.generateIR();

        //Assert
        assertEquals(errors.toString(), eTest.getErrors().toString());

    }
}

