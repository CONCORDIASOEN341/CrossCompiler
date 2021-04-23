package com.github.ConcordiaSOEN341.CodeGen;

import com.github.ConcordiaSOEN341.CrossAssembler.CommandHandler;
import com.github.ConcordiaSOEN341.Error.ErrorReporter;
import com.github.ConcordiaSOEN341.Error.IErrorReporter;
import com.github.ConcordiaSOEN341.Lexer.Token;
import com.github.ConcordiaSOEN341.Logger.LoggerFactory;
import com.github.ConcordiaSOEN341.Parser.*;
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
        irTest.add(new LineStatement());
        init(irTest);

        String[] content = codeGenTest.listingIRLabel(irTest);
        assertEquals(irTest.get(1).getLabel().getTokenString(), content[2].trim());
        assertEquals(irTest.get(2).getLabel().getTokenString(), content[3].trim());
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
    public void listingIRMNE_whenIRisNotEmpty_ExpectsSameContentInListingFile() {
        irTest = new ArrayList<>();
        irTest.add(new LineStatement());
        irTest.add(new LineStatement(new Instruction(new Token("lda.i16"), new Token("Msg1"), InstructionType.RELATIVE)));
        irTest.add(new LineStatement(new Instruction(new Token("ldc.i8"), new Token("12"), InstructionType.RELATIVE)));
        irTest.add(new LineStatement());

        init(irTest);

        String[] content = codeGenTest.listingIRMne(irTest);
        assertEquals(irTest.get(1).getInstruction().getMnemonic().getTokenString(), content[2].trim());
        assertEquals(irTest.get(2).getInstruction().getMnemonic().getTokenString(), content[3].trim());
    }

    @Test
    public void listingIROps_whenIrIsEmpty_expectEmptyArray() {
        irTest = new ArrayList<>();
        init(irTest);

        String[] irOPS = codeGenTest.listingIROps(irTest);
        assertEquals(0, irOPS.length);
    }

    @Test
    public void listingIROps_whenIRisNotEmpty_ExpectsSameContentInListingFile() {
        irTest = new ArrayList<>();
        irTest.add(new LineStatement());
        irTest.add(new LineStatement(new Instruction(new Token("lda.i16"), new Token("Msg1"), InstructionType.RELATIVE)));
        irTest.add(new LineStatement(new Instruction(new Token("ldc.i8"), new Token("12"), InstructionType.RELATIVE)));
        irTest.add(new LineStatement());
        init(irTest);

        String[] content = codeGenTest.listingIROps(irTest);
        assertEquals(irTest.get(1).getInstruction().getOperand().getTokenString(), content[2].trim());
        assertEquals(irTest.get(2).getInstruction().getOperand().getTokenString(), content[3].trim());
    }

    @Test
    public void listingIRComments_whenIrIsEmpty_expectEmptyArray() {
        irTest = new ArrayList<>();
        init(irTest);

        String[] irComments = codeGenTest.listingIRComments(irTest);
        assertEquals(0, irComments.length);
    }

    @Test
    public void listingIRComments_whenIRisNotEmpty_ExpectsSameContentInListingFile() {
        irTest = new ArrayList<>();
        irTest.add(new LineStatement());
        irTest.add(new LineStatement(new Instruction(new Token("lda.i16"), new Token("Msg1"), InstructionType.RELATIVE)));
        irTest.get(1).setComment(new Token(";This is not a test"));
        irTest.add(new LineStatement(new Instruction(new Token("ldc.i8"), new Token("12"), InstructionType.RELATIVE)));
        irTest.add(new LineStatement());

        init(irTest);

        String[] content = codeGenTest.listingIRComments(irTest);
        assertEquals(irTest.get(1).getComment().getTokenString(), content[2].trim());
        assertEquals(irTest.get(2).getComment().getTokenString(), content[3].trim());
    }


}