package com.github.ConcordiaSOEN341.Parser;

import com.github.ConcordiaSOEN341.CodeGen.IOpCodeTableElement;
import com.github.ConcordiaSOEN341.CodeGen.OpCodeTableElement;
import com.github.ConcordiaSOEN341.CodeGen.SymbolTable;
import com.github.ConcordiaSOEN341.CrossAssembler.CommandHandler;
import com.github.ConcordiaSOEN341.Error.ErrorReporter;
import com.github.ConcordiaSOEN341.Error.IErrorReporter;
import com.github.ConcordiaSOEN341.Lexer.*;
import com.github.ConcordiaSOEN341.Logger.LoggerFactory;
import com.github.ConcordiaSOEN341.Reader.IReader;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ParserIntegrationTest {

    private ArrayList<Character> file;
    private ArrayList<ILineStatement> irTest;
    private IParser pTest;

    private void initIR(ArrayList<Character> input){
        LoggerFactory lFTest = new LoggerFactory(new CommandHandler());
        SymbolTable sTest = new SymbolTable();
        IErrorReporter eTest = new ErrorReporter(lFTest);
        ParserFSM pFSMTest = new ParserFSM(lFTest);
        IReader rTest = new ReaderMoq(input);
        LexerFSM fsmTest = new LexerFSM(rTest, lFTest);
        ILexer lTest = new Lexer(sTest, fsmTest, rTest, lFTest, eTest);
        pTest = new Parser(pFSMTest, lTest, sTest, lFTest, eTest);
    }

    private void initOpCodeTable(ArrayList<ILineStatement> ir){
        LoggerFactory lFTest = new LoggerFactory(new CommandHandler());
        SymbolTable sTest = new SymbolTable();
        IErrorReporter eTest = new ErrorReporter(lFTest);
        ParserFSM pFSMTest = new ParserFSM(lFTest);
        pTest = new Parser(pFSMTest, sTest, lFTest, eTest);
        pTest.setIR(ir);
    }

    @Test
    public void parse_giveEmptyFile_expectEmpty(){
        // Arrange
        file = new ArrayList<>();
        file.add('~');

        // Act
        initIR(file);
        ArrayList<ILineStatement> lineStatements = pTest.generateIR();
        // Assert
        assertEquals("", lineStatements.get(0).getInstruction().getMnemonic().getTokenString());

    }

    @Test
    public void parse_giveEmptyLine_expectEmpty(){
        // Arrange
        tokenList = new ArrayList<>();
        tokenList.add(new Token("", new Position(1, 1, 1), TokenType.EOL));
        tokenList.add(new Token("", new Position(2, 1, 1), TokenType.EOF));
        // Act
        initIR(tokenList);
        ArrayList<ILineStatement> lineStatements = pTest.generateIR();
        // Assert
        assertEquals("", lineStatements.get(0).getInstruction().getMnemonic().getTokenString());

    }

    @Test
    public void parse_giveInherentInstructionType_expectInherentInstructionType(){
        tokenList = new ArrayList<>();
        tokenList.add(new Token("pop", new Position(1, 0, 4), TokenType.MNEMONIC));
        tokenList.add(new Token("", new Position(1, 4, 4), TokenType.EOL));
        tokenList.add(new Token("", new Position(1, 3, 3), TokenType.EOF));

        initIR(tokenList);
        ArrayList<ILineStatement> lineStatements = pTest.generateIR();

        assertEquals(lineStatements.get(0).getInstruction().getInstructionType(), InstructionType.INHERENT);

    }

    @Test
    public void parse_giveImmediateInstructionType_expectImmediateInstructionType(){
        tokenList = new ArrayList<>();
        tokenList.add(new Token("addv.u3", new Position(1, 0, 4), TokenType.MNEMONIC));
        tokenList.add(new Token("2", new Position(1, 0, 4), TokenType.OFFSET));
        tokenList.add(new Token("", new Position(1, 4, 4), TokenType.EOL));
        tokenList.add(new Token("", new Position(1, 3, 3), TokenType.EOF));

        initIR(tokenList);
        ArrayList<ILineStatement> lineStatements = pTest.generateIR();

        assertEquals(lineStatements.get(0).getInstruction().getInstructionType(), InstructionType.IMMEDIATE);

    }

    @Test
    public void parse_giveRelativeInstructionType_expectRelativeInstructionType(){
        tokenList = new ArrayList<>();
        tokenList.add(new Token("enter.u8", new Position(1, 0, 4), TokenType.MNEMONIC));
        tokenList.add(new Token("25", new Position(1, 0, 4), TokenType.OFFSET));
        tokenList.add(new Token("", new Position(1, 4, 4), TokenType.EOL));
        tokenList.add(new Token("", new Position(1, 3, 3), TokenType.EOF));

        initIR(tokenList);
        ArrayList<ILineStatement> lineStatements = pTest.generateIR();

        assertEquals(lineStatements.get(0).getInstruction().getInstructionType(), InstructionType.RELATIVE);

    }

    @Test
    public void parse_giveListWithLabel_expectSameLabel(){
        tokenList = new ArrayList<>();
        tokenList.add(new Token("Msg1", new Position(1, 0, 4), TokenType.LABEL));
        tokenList.add(new Token("", new Position(1, 9, 9), TokenType.EOL));
        tokenList.add(new Token("", new Position(2, 0, 0), TokenType.EOF));

        initIR(tokenList);
        ArrayList<ILineStatement> lineStatements = pTest.generateIR();

        assertEquals("Msg1", lineStatements.get(0).getLabel().getTokenString());

    }

    @Test
    public void parse_giveListWithDirective_expectSameDirective(){
        tokenList = new ArrayList<>();
        tokenList.add(new Token(".cstring", new Position(1, 0, 4), TokenType.DIRECTIVE));
        tokenList.add(new Token("ABCD1", new Position(1, 4, 8), TokenType.CSTRING));
        tokenList.add(new Token("", new Position(1, 9, 9), TokenType.EOL));
        tokenList.add(new Token("", new Position(2, 0, 0), TokenType.EOF));

        initIR(tokenList);
        ArrayList<ILineStatement> lineStatements = pTest.generateIR();

        assertEquals("ABCD1", lineStatements.get(0).getDirective().getCString().getTokenString());

    }

    @Test
    public void parse_giveListWithComment_expectSameComment(){
        tokenList = new ArrayList<>();
        tokenList.add(new Token("A comment", new Position(1, 0, 4), TokenType.COMMENT));
        tokenList.add(new Token("", new Position(1, 4, 4), TokenType.EOL));
        tokenList.add(new Token("", new Position(2, 3, 3), TokenType.EOF));

        initIR(tokenList);
        ArrayList<ILineStatement> lineStatements = pTest.generateIR();

        assertEquals("A comment", lineStatements.get(0).getComment().getTokenString());

    }

    @Test
    public void generateOpCodeTable_SecondPassLabels(){
        // Arrange
        irTest = new ArrayList<>();
        irTest.add(new LineStatement(new Instruction(new Token("lda.i16"), new Token("Msg1"), InstructionType.RELATIVE)));
        irTest.add(new LineStatement(new Instruction(new Token("ldc.i8"), new Token("12"), InstructionType.RELATIVE)));
        irTest.add(new LineStatement(new Token("Msg1"), new Instruction(new Token("ldc.i8"), new Token("12"), InstructionType.RELATIVE)));
        initOpCodeTable(irTest);

        ArrayList<IOpCodeTableElement> expectedOpTable = new ArrayList<>();
        expectedOpTable.add(new OpCodeTableElement(1, "0000", "D5", 4, "Msg1"));
        expectedOpTable.get(0).addOperand("0005");
        expectedOpTable.add(new OpCodeTableElement(2, "0003", "D9", 2, null));
        expectedOpTable.get(1).addOperand("0C");
        expectedOpTable.add(new OpCodeTableElement(3, "0005", "D9", 2, null));
        expectedOpTable.get(2).addOperand("0C");
        // Act
        ArrayList<IOpCodeTableElement> actualOpTable = pTest.generateOpCodeTable();

        // Assert
        assertEquals(expectedOpTable.toString(), actualOpTable.toString());
    }

    @Test
    public void generateOpCodeTable_FwdAndBwdBranching(){
        // Arrange
        irTest = new ArrayList<>();
        irTest.add(new LineStatement(new Token("Main"), new Instruction(new Token("br.i8"), new Token("Main"), InstructionType.RELATIVE)));
        irTest.add(new LineStatement(new Instruction(new Token("br.i8"), new Token("Main"), InstructionType.RELATIVE)));
        irTest.add(new LineStatement(new Instruction(new Token("br.i8"), new Token("End"), InstructionType.RELATIVE)));
        irTest.add(new LineStatement(new Token("End"), new Instruction(new Token("br.i8"), new Token("End"), InstructionType.RELATIVE)));
        initOpCodeTable(irTest);

        ArrayList<IOpCodeTableElement> expectedOpTable = new ArrayList<>();
        expectedOpTable.add(new OpCodeTableElement(1, "0000", "E0", 2, "Main"));
        expectedOpTable.get(0).addOperand("00");
        expectedOpTable.add(new OpCodeTableElement(2, "0002", "E0", 2, "Main"));
        expectedOpTable.get(1).addOperand("FE");
        expectedOpTable.add(new OpCodeTableElement(3, "0004", "E0", 2, "End"));
        expectedOpTable.get(2).addOperand("02");
        expectedOpTable.add(new OpCodeTableElement(4, "0006", "E0", 2, "End"));
        expectedOpTable.get(3).addOperand("00");
        // Act
        ArrayList<IOpCodeTableElement> actualOpTable = pTest.generateOpCodeTable();

        // Assert
        assertEquals(expectedOpTable.toString(), actualOpTable.toString());
    }

    @Test
    public void generateOpCodeTable_Relative(){
        // Arrange
        irTest = new ArrayList<>();
        irTest.add(new LineStatement(new Instruction(new Token("lda.i16"), new Token("Msg1"), InstructionType.RELATIVE)));
        irTest.add(new LineStatement(new Instruction(new Token("ldc.i8"), new Token("12"), InstructionType.RELATIVE)));
        initOpCodeTable(irTest);

        ArrayList<IOpCodeTableElement> expectedOpTable = new ArrayList<>();
        expectedOpTable.add(new OpCodeTableElement(1, "0000", "D5", 4, "Msg1"));
        expectedOpTable.add(new OpCodeTableElement(2, "0003", "D9", 2, null));
        expectedOpTable.get(1).addOperand("0C");

        // Act
        ArrayList<IOpCodeTableElement> actualOpTable = pTest.generateOpCodeTable();

        // Assert
        assertEquals(expectedOpTable.toString(), actualOpTable.toString());
    }

    @Test
    public void generateOpCodeTable_Immediate(){
        // Arrange
        irTest = new ArrayList<>();
        irTest.add(new LineStatement(new Instruction(new Token("enter.u5"), new Token("16"), InstructionType.IMMEDIATE)));
        irTest.add(new LineStatement(new Instruction(new Token("ldc.i3"), new Token("2"), InstructionType.IMMEDIATE)));
        initOpCodeTable(irTest);

        ArrayList<IOpCodeTableElement> expectedOpTable = new ArrayList<>();
        expectedOpTable.add(new OpCodeTableElement(1, "0000", "70", 0, null));
        expectedOpTable.add(new OpCodeTableElement(2, "0001", "92", 0, null));

        // Act
        ArrayList<IOpCodeTableElement> actualOpTable = pTest.generateOpCodeTable();

        // Assert
        assertEquals(expectedOpTable.toString(), actualOpTable.toString());
    }

    @Test
    public void generateOpCodeTable_Inherent() {
        // Arrange
        irTest = new ArrayList<>();
        irTest.add(new LineStatement(new Instruction(new Token("halt"), InstructionType.INHERENT)));
        initOpCodeTable(irTest);

        ArrayList<IOpCodeTableElement> expectedOpTable = new ArrayList<>();
        expectedOpTable.add(new OpCodeTableElement(1, "0000", "00", 0, null));

        // Act
        ArrayList<IOpCodeTableElement> actualOpTable = pTest.generateOpCodeTable();

        // Assert
        assertEquals(expectedOpTable.toString(), actualOpTable.toString());
    }

    @Test
    public void generateOpCodeTable_NoInstruction(){
        // Arrange
        irTest = new ArrayList<>();
        LineStatement l = new LineStatement();
        l.setDirective(new Directive(new Token(".cstring"),new Token("A1")));
        irTest.add(l);
        initOpCodeTable(irTest);

        ArrayList<IOpCodeTableElement> expectedOpTable = new ArrayList<>();
        expectedOpTable.add(new OpCodeTableElement(1, "0000", "", 0, null));
        expectedOpTable.get(0).addOperand("41"); expectedOpTable.get(0).addOperand("31"); expectedOpTable.get(0).addOperand("00");


        // Act
        ArrayList<IOpCodeTableElement> actualOpTable = pTest.generateOpCodeTable();

        // Assert
        assertEquals(expectedOpTable.toString(), actualOpTable.toString());
    }

    @Test
    public void generateOpCodeTable_OperandList(){
        // Arrange
        LineStatement l = new LineStatement();
        l.setComment(new Token(";Hello"));
        irTest = new ArrayList<>();
        irTest.add(l);
        irTest.add(new LineStatement(new Instruction(new Token("ldc.i3"), new Token("2"), InstructionType.IMMEDIATE)));
        initOpCodeTable(irTest);

        ArrayList<IOpCodeTableElement> expectedOpTable = new ArrayList<>();
        expectedOpTable.add(new OpCodeTableElement(1, "0000", "", 0, null));
        expectedOpTable.add(new OpCodeTableElement(2, "0000", "92", 0, null));

        // Act
        ArrayList<IOpCodeTableElement> actualOpTable = pTest.generateOpCodeTable();

        // Assert
        assertEquals(expectedOpTable.toString(), actualOpTable.toString());
    }
}