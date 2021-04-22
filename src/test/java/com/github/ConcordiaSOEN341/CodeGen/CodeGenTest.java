package com.github.ConcordiaSOEN341.CodeGen;

import com.github.ConcordiaSOEN341.CrossAssembler.CommandHandler;
import com.github.ConcordiaSOEN341.Error.ErrorReporter;
import com.github.ConcordiaSOEN341.Error.IErrorReporter;
import com.github.ConcordiaSOEN341.Lexer.Token;
import com.github.ConcordiaSOEN341.Logger.LoggerFactory;
import com.github.ConcordiaSOEN341.Parser.ILineStatement;
import com.github.ConcordiaSOEN341.Parser.Instruction;
import com.github.ConcordiaSOEN341.Parser.InstructionType;
import com.github.ConcordiaSOEN341.Parser.LineStatement;
import junit.framework.TestCase;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class CodeGenTest extends TestCase {
    ICodeGen codeGenTest;
    ArrayList<ILineStatement> irTest;
    SymbolTable sTest;
    IErrorReporter eTest;
    LoggerFactory lFTest;

    private void init(ArrayList<ILineStatement> ir) {
        lFTest = new LoggerFactory(new CommandHandler());
        sTest = new SymbolTable();
        eTest = new ErrorReporter(lFTest);
        codeGenTest = new CodeGen(lFTest, eTest);
    }

    @Test
    public void generateByteCode_whenIOpCodeTableElementEmpty_expectEmptyString() {
        irTest = new ArrayList<>();
        irTest.add(new LineStatement(new Instruction(new Token("lda.i16"), new Token("Msg1"), InstructionType.RELATIVE)));
        irTest.add(new LineStatement(new Instruction(new Token("ldc.i8"), new Token("12"), InstructionType.RELATIVE)));
        irTest.add(new LineStatement(new Token("Msg1"), new Instruction(new Token("ldc.i8"), new Token("12"), InstructionType.RELATIVE)));
        init(irTest);

        ArrayList<IOpCodeTableElement> opCodeTable = new ArrayList<>();

        String byteCode = codeGenTest.generateByteCode(opCodeTable);
        assertTrue(StringUtils.isEmpty(byteCode));
    }

    @Test
    public void generateByteCode_whenIOpCodeTableElementNotEmpty_expectEmptyString() {
        irTest = new ArrayList<>();
        irTest.add(new LineStatement(new Instruction(new Token("lda.i16"), new Token("Msg1"), InstructionType.RELATIVE)));
        irTest.add(new LineStatement(new Instruction(new Token("ldc.i8"), new Token("12"), InstructionType.RELATIVE)));
        irTest.add(new LineStatement(new Token("Msg1"), new Instruction(new Token("ldc.i8"), new Token("12"), InstructionType.RELATIVE)));
        init(irTest);

        ArrayList<IOpCodeTableElement> expectedOpTable = new ArrayList<>();
        expectedOpTable.add(new OpCodeTableElement(1, "0000", "D5", 4, "Msg1"));
        expectedOpTable.get(0).addOperand("0005");
        expectedOpTable.add(new OpCodeTableElement(2, "0003", "D9", 2, null));
        expectedOpTable.get(1).addOperand("0C");
        expectedOpTable.add(new OpCodeTableElement(3, "0005", "D9", 2, null));
        expectedOpTable.get(2).addOperand("0C");

        String expected = expectedOpTable.get(0).getOpCode() + expectedOpTable.get(0).getOperands().iterator().next() + expectedOpTable.get(1).getOpCode() + expectedOpTable.get(1).getOperands().iterator().next() + expectedOpTable.get(2).getOpCode() + expectedOpTable.get(2).getOperands().iterator().next();

        assertEquals(expected, codeGenTest.generateByteCode(expectedOpTable));
    }

    @Test
    public void listingOP_whenIrAndOpCodeTableNotEmpty_expectPartOfListingFile(){
        irTest = new ArrayList<>();
        irTest.add(new LineStatement(new Instruction(new Token("lda.i16"), new Token("Msg1"), InstructionType.RELATIVE)));
        irTest.add(new LineStatement(new Instruction(new Token("ldc.i8"), new Token("12"), InstructionType.RELATIVE)));
        irTest.add(new LineStatement(new Token("Msg1"), new Instruction(new Token("ldc.i8"), new Token("12"), InstructionType.RELATIVE)));
        init(irTest);

        ArrayList<IOpCodeTableElement> opTable = new ArrayList<>();
        opTable.add(new OpCodeTableElement(1, "0000", "D5", 4, "Msg1"));
        opTable.get(0).addOperand("0005");
        opTable.add(new OpCodeTableElement(2, "0003", "D9", 2, null));
        opTable.get(1).addOperand("0C");
        opTable.add(new OpCodeTableElement(3, "0005", "D9", 2, null));
        opTable.get(2).addOperand("0C");

        String[] expectedOpTable = codeGenTest.listingOP(irTest, opTable);
        assertEquals(opTable.size(), expectedOpTable.length);
        assertEquals("Line Addr Code", expectedOpTable[0].trim());
        assertEquals("1\t " + opTable.get(0).getAddress() + " " + opTable.get(0).getOpCode() + " " + opTable.get(0).getOperands().get(0), expectedOpTable[1].trim());
        assertEquals("2\t " + opTable.get(1).getAddress() + " " + opTable.get(1).getOpCode() + " " + opTable.get(1).getOperands().get(0), expectedOpTable[2].trim());
    }

    @Test
    public void listingOP_whenIrIsEmptyAndOpCodeTableNotEmpty_expectEmptyOpTable() {
        irTest = new ArrayList<>();
        irTest.add(new LineStatement(new Instruction(new Token("lda.i16"), new Token("Msg1"), InstructionType.RELATIVE)));
        irTest.add(new LineStatement(new Instruction(new Token("ldc.i8"), new Token("12"), InstructionType.RELATIVE)));
        irTest.add(new LineStatement(new Token("Msg1"), new Instruction(new Token("ldc.i8"), new Token("12"), InstructionType.RELATIVE)));
        init(irTest);

        ArrayList<IOpCodeTableElement> opTable = new ArrayList<>();

        String[] expectedOpTable = codeGenTest.listingOP(irTest, opTable);
        assertEquals(0, expectedOpTable.length);
    }

    @Test
    public void listingOP_whenIrIsNotEmptyAndOpCodeTableIsEmpty_expectEmptyOpTable() {
        irTest = new ArrayList<>();
        init(irTest);

        ArrayList<IOpCodeTableElement> opTable = new ArrayList<>();
        opTable.add(new OpCodeTableElement(1, "0000", "D5", 4, "Msg1"));
        opTable.get(0).addOperand("0005");
        opTable.add(new OpCodeTableElement(2, "0003", "D9", 2, null));
        opTable.get(1).addOperand("0C");
        opTable.add(new OpCodeTableElement(3, "0005", "D9", 2, null));
        opTable.get(2).addOperand("0C");

        String[] expectedOpTable = codeGenTest.listingOP(irTest, opTable);
        assertEquals(0, expectedOpTable.length);
    }

    @Test
    public void listingIRLabel_whenIrIsNotEmpty_expectArrayOfSameSize() {
        irTest = new ArrayList<>();
        irTest.add(new LineStatement());
        irTest.add(new LineStatement(new Token("Msg1"), new Instruction(new Token("ldc.i8"), new Token("12"), InstructionType.RELATIVE)));
        irTest.add(new LineStatement(new Token("Msg2"), new Instruction(new Token("ldc.i8"), new Token("12"), InstructionType.RELATIVE)));
        init(irTest);

        String expected = "";
        expected = irTest.get(0).getLabel().getTokenString();
        String[] content = codeGenTest.listingIRLabel(irTest);
        assertEquals(irTest.size(), codeGenTest.listingIRLabel(irTest).length);
        //assertEquals("Label", codeGenTest.listingIRLabel(irTest).  );
        //assertEquals("1\t " + opTable.get(0).getAddress() + " " + opTable.get(0).getOpCode() + " " + opTable.get(0).getOperands().get(0), expectedOpTable[1].trim());
       // assertEquals("2\t " + opTable.get(1).getAddress() + " " + opTable.get(1).getOpCode() + " " + opTable.get(1).getOperands().get(0), expectedOpTable[2].trim());

    }

    @Test
    public void listingIRLabel_whenIrIsEmpty_expectEmptyArray() {
        irTest = new ArrayList<>();
        init(irTest);

        String[] irLabels = codeGenTest.listingIRLabel(irTest);
        assertEquals(0, irLabels.length);
    }

    @Test
    public void listingIRMNE_whenIrIsEmpty_expectEmptyArray() {
        irTest = new ArrayList<>();
        init(irTest);

        String[] irMNE = codeGenTest.listingIRMne(irTest);
        assertEquals(0, irMNE.length);
    }

    @Test
    public void listingIRMNE() {
        irTest = new ArrayList<>();
        irTest.add(new LineStatement(new Instruction(new Token("lda.i16"), new Token("Msg1"), InstructionType.RELATIVE)));
        irTest.add(new LineStatement(new Instruction(new Token("ldc.i8"), new Token("12"), InstructionType.RELATIVE)));
        init(irTest);

        String expected = "";
       // expected = irTest.get(0).getInstruction().getTokenString();
        String[] blabla = codeGenTest.listingIRLabel(irTest);
        assertEquals(irTest.size(), codeGenTest.listingIRLabel(irTest).length);
        //assertNull(expected);
    }

    @Test
    public void listingIROps_whenIrIsEmpty_expectEmptyArray() {
        irTest = new ArrayList<>();
        init(irTest);

        String[] irOPS = codeGenTest.listingIROps(irTest);
        assertEquals(0, irOPS.length);
    }

    @Test
    public void listingIRComments_whenIrIsEmpty_expectEmptyArray() {
        irTest = new ArrayList<>();
        init(irTest);

        String[] irComments = codeGenTest.listingIRComments(irTest);
        assertEquals(0, irComments.length);
    }
//    @Test
//    public void generateOpCodeTable_SecondPassLabels(){
    // Arrange
//        irTest = new ArrayList<>();
//        irTest.add(new LineStatement(new Instruction(new Token("lda.i16"), new Token("Msg1"), InstructionType.RELATIVE)));
//        irTest.add(new LineStatement(new Instruction(new Token("ldc.i8"), new Token("12"), InstructionType.RELATIVE)));
//        irTest.add(new LineStatement(new Token("Msg1"), new Instruction(new Token("ldc.i8"), new Token("12"), InstructionType.RELATIVE)));
//        init(irTest);
//
//        ArrayList<IOpCodeTableElement> expectedOpTable = new ArrayList<>();
//        expectedOpTable.add(new OpCodeTableElement(1, "0000", "D5", 4, "Msg1"));
//        expectedOpTable.get(0).addOperand("0005");
//        expectedOpTable.add(new OpCodeTableElement(2, "0003", "D9", 2, null));
//        expectedOpTable.get(1).addOperand("0C");
//        expectedOpTable.add(new OpCodeTableElement(3, "0005", "D9", 2, null));
//        expectedOpTable.get(2).addOperand("0C");
//        // Act
//        ArrayList<IOpCodeTableElement> actualOpTable = codeGenTest.generateOpCodeTable();

    // Assert
//        assertEquals("test", "test");
//    }
//
//    @Test
//    public void generateOpCodeTable_FwdAndBwdBranching(){
//        // Arrange
//        irTest = new ArrayList<>();
//        irTest.add(new LineStatement(new Token("Main"), new Instruction(new Token("br.i8"), new Token("Main"), InstructionType.RELATIVE)));
//        irTest.add(new LineStatement(new Instruction(new Token("br.i8"), new Token("Main"), InstructionType.RELATIVE)));
//        irTest.add(new LineStatement(new Instruction(new Token("br.i8"), new Token("End"), InstructionType.RELATIVE)));
//        irTest.add(new LineStatement(new Token("End"), new Instruction(new Token("br.i8"), new Token("End"), InstructionType.RELATIVE)));
//        init(irTest);
//
//        ArrayList<IOpCodeTableElement> expectedOpTable = new ArrayList<>();
//        expectedOpTable.add(new OpCodeTableElement(1, "0000", "E0", 2, "Main"));
//        expectedOpTable.get(0).addOperand("00");
//        expectedOpTable.add(new OpCodeTableElement(2, "0002", "E0", 2, "Main"));
//        expectedOpTable.get(1).addOperand("FE");
//        expectedOpTable.add(new OpCodeTableElement(3, "0004", "E0", 2, "End"));
//        expectedOpTable.get(2).addOperand("02");
//        expectedOpTable.add(new OpCodeTableElement(4, "0006", "E0", 2, "End"));
//        expectedOpTable.get(3).addOperand("00");
//        // Act
//        ArrayList<IOpCodeTableElement> actualOpTable = codeGenTest.generateOpCodeTable();
//
//        // Assert
//        assertEquals(expectedOpTable.toString(), actualOpTable.toString());
//    }
//
//    @Test
//    public void generateOpCodeTable_Relative(){
//        // Arrange
//        irTest = new ArrayList<>();
//        irTest.add(new LineStatement(new Instruction(new Token("lda.i16"), new Token("Msg1"), InstructionType.RELATIVE)));
//        irTest.add(new LineStatement(new Instruction(new Token("ldc.i8"), new Token("12"), InstructionType.RELATIVE)));
//        init(irTest);
//
//        ArrayList<IOpCodeTableElement> expectedOpTable = new ArrayList<>();
//        expectedOpTable.add(new OpCodeTableElement(1, "0000", "D5", 4, "Msg1"));
//        expectedOpTable.add(new OpCodeTableElement(2, "0003", "D9", 2, null));
//        expectedOpTable.get(1).addOperand("0C");
//
//        // Act
//        ArrayList<IOpCodeTableElement> actualOpTable = codeGenTest.generateOpCodeTable();
//
//        // Assert
//        assertEquals(expectedOpTable.toString(), actualOpTable.toString());
//    }
//
//    @Test
//    public void generateOpCodeTable_Immediate(){
//        // Arrange
//        irTest = new ArrayList<>();
//        irTest.add(new LineStatement(new Instruction(new Token("enter.u5"), new Token("16"), InstructionType.IMMEDIATE)));
//        irTest.add(new LineStatement(new Instruction(new Token("ldc.i3"), new Token("2"), InstructionType.IMMEDIATE)));
//        init(irTest);
//
//        ArrayList<IOpCodeTableElement> expectedOpTable = new ArrayList<>();
//        expectedOpTable.add(new OpCodeTableElement(1, "0000", "70", 0, null));
//        expectedOpTable.add(new OpCodeTableElement(2, "0001", "92", 0, null));
//
//        // Act
//        ArrayList<IOpCodeTableElement> actualOpTable = codeGenTest.generateOpCodeTable();
//
//        // Assert
//        assertEquals(expectedOpTable.toString(), actualOpTable.toString());
//    }
//
//    @Test
//    public void generateOpCodeTable_Inherent() {
//        // Arrange
//        irTest = new ArrayList<>();
//        irTest.add(new LineStatement(new Instruction(new Token("halt"), InstructionType.INHERENT)));
//        init(irTest);
//
//        ArrayList<IOpCodeTableElement> expectedOpTable = new ArrayList<>();
//        expectedOpTable.add(new OpCodeTableElement(1, "0000", "00", 0, null));
//
//        // Act
//        ArrayList<IOpCodeTableElement> actualOpTable = codeGenTest.generateOpCodeTable();
//
//        // Assert
//        assertEquals(expectedOpTable.toString(), actualOpTable.toString());
//    }
//
//    @Test
//    public void generateOpCodeTable_NoInstruction(){
//        // Arrange
//        irTest = new ArrayList<>();
//        LineStatement l = new LineStatement();
//        l.setDirective(new Directive(new Token(".cstring"),new Token("A1")));
//        irTest.add(l);
//        init(irTest);
//
//        ArrayList<IOpCodeTableElement> expectedOpTable = new ArrayList<>();
//        expectedOpTable.add(new OpCodeTableElement(1, "0000", "", 0, null));
//        expectedOpTable.get(0).addOperand("41"); expectedOpTable.get(0).addOperand("31"); expectedOpTable.get(0).addOperand("00");
//
//
//        // Act
//        ArrayList<IOpCodeTableElement> actualOpTable = codeGenTest.generateOpCodeTable();
//
//        // Assert
//        assertEquals(expectedOpTable.toString(), actualOpTable.toString());
//    }
//
//    @Test
//    public void generateOpCodeTable_OperandList(){
//        // Arrange
//        LineStatement l = new LineStatement();
//        l.setComment(new Token(";Hello"));
//        irTest = new ArrayList<>();
//        irTest.add(l);
//        irTest.add(new LineStatement(new Instruction(new Token("ldc.i3"), new Token("2"), InstructionType.IMMEDIATE)));
//        init(irTest);
//
//        ArrayList<IOpCodeTableElement> expectedOpTable = new ArrayList<>();
//        expectedOpTable.add(new OpCodeTableElement(1, "0000", "", 0, null));
//        expectedOpTable.add(new OpCodeTableElement(2, "0000", "92", 0, null));
//
//        // Act
//        ArrayList<IOpCodeTableElement> actualOpTable = codeGenTest.generateOpCodeTable();
//
//        // Assert
//        assertEquals(expectedOpTable.toString(), actualOpTable.toString());
//    }


//    @Test
//    public void listingsTesting() {
//        String testToken1 = "halt";
//        String testToken2 = "or";
//        String testToken4 = "; comment";
//        String testToken3 = "EOL";
//        String testToken5 = "2";
//
//        IToken t1 = new Token(testToken1, new Position(1, 1, testToken1.length()), TokenType.MNEMONIC);
//        IToken t2 = new Token(testToken2, new Position(1, 1, testToken2.length()), TokenType.MNEMONIC);
//        IToken t4 = new Token(testToken4, new Position(1, 3, testToken2.length()), TokenType.COMMENT);
//        IToken t5 = new Token(testToken5, new Position(1, 2, testToken2.length()), TokenType.OFFSET);
//
//        IToken t3 = new Token(testToken3, new Position(1, 1, testToken3.length()), TokenType.EOL);
//
//        IInstruction I1 = new Instruction(t1, t5, InstructionType.IMMEDIATE);
//        IInstruction I2 = new Instruction(t2, t5, InstructionType.IMMEDIATE);
//
//        ArrayList<IToken> t = new ArrayList<>();
//        t.add(t1);
//        t.add(t2);
//        t.add(t3);
//        t.add(t4);
//        t.add(t5);
//        ILineStatement l1 = new LineStatement(I1, t3, t4);
//        ILineStatement l2 = new LineStatement(I2, t3, t4);
//
//        ArrayList<ILineStatement> ir = new ArrayList<>();
//        ir.add(0, l1);
//        ir.add(1, l2);
//
//        ICodeGen cg = new CodeGen();
//
//        String[] test1 = cg.listing(ir);
//
//        assertEquals("1\t 0000 00 \t\t\t  \t\t\t  halt \t 2\t\t\t\t; comment \t\n", test1[0].toString());
//        assertEquals("2\t 0001 0E \t\t\t  \t\t\t  or \t 2\t\t\t\t; comment \t\n", test1[1].toString());
//    }


}