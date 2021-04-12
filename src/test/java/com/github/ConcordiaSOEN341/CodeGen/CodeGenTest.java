package com.github.ConcordiaSOEN341.CodeGen;

import com.github.ConcordiaSOEN341.Interfaces.*;
import com.github.ConcordiaSOEN341.Lexer.Position;
import com.github.ConcordiaSOEN341.Lexer.Token;
import com.github.ConcordiaSOEN341.Lexer.TokenType;
import com.github.ConcordiaSOEN341.Parser.Directive;
import com.github.ConcordiaSOEN341.Parser.Instruction;
import com.github.ConcordiaSOEN341.Parser.InstructionType;
import com.github.ConcordiaSOEN341.Parser.LineStatement;
import com.github.ConcordiaSOEN341.Tables.OpCodeTableElement;
import com.github.ConcordiaSOEN341.Tables.SymbolTable;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;

@RunWith(MockitoJUnitRunner.class)
public class CodeGenTest extends TestCase {

    ArrayList<ILineStatement> irTest;
    SymbolTable sTest;
    ICodeGen codeGenTest;

    @Test
    public void generateOpCodeTable_SecondPassLabels(){
        // Arrange
        irTest = new ArrayList<>();
        irTest.add(new LineStatement(new Instruction(new Token("lda.i16"), new Token("Msg1"), InstructionType.RELATIVE)));
        irTest.add(new LineStatement(new Instruction(new Token("ldc.i8"), new Token("12"), InstructionType.RELATIVE)));
        irTest.add(new LineStatement(new Token("Msg1"), new Instruction(new Token("ldc.i8"), new Token("12"), InstructionType.RELATIVE)));
        sTest = new SymbolTable();
        codeGenTest = new CodeGen(sTest);

        HashMap<Integer, IOpCodeTableElement> expectedOpTable = new HashMap<>();
        expectedOpTable.put(1, new OpCodeTableElement(1, "0000", "D5", 4, "Msg1"));
        expectedOpTable.get(1).addOperand("0005");
        expectedOpTable.put(2, new OpCodeTableElement(2, "0003", "D9", 2, null));
        expectedOpTable.get(2).addOperand("0C");
        expectedOpTable.put(3, new OpCodeTableElement(3, "0005", "D9", 2, null));
        expectedOpTable.get(3).addOperand("0C");
        // Act
        HashMap<Integer, IOpCodeTableElement> actualOpTable = codeGenTest.generateOpCodeTable(irTest);

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
        sTest = new SymbolTable();
        codeGenTest = new CodeGen(sTest);

        HashMap<Integer, IOpCodeTableElement> expectedOpTable = new HashMap<>();
        expectedOpTable.put(1, new OpCodeTableElement(1, "0000", "E0", 2, "Main"));
        expectedOpTable.get(1).addOperand("00");
        expectedOpTable.put(2, new OpCodeTableElement(2, "0002", "E0", 2, "Main"));
        expectedOpTable.get(2).addOperand("FE");
        expectedOpTable.put(3, new OpCodeTableElement(3, "0004", "E0", 2, "End"));
        expectedOpTable.get(3).addOperand("02");
        expectedOpTable.put(4, new OpCodeTableElement(4, "0006", "E0", 2, "End"));
        expectedOpTable.get(4).addOperand("00");
        // Act
        HashMap<Integer, IOpCodeTableElement> actualOpTable = codeGenTest.generateOpCodeTable(irTest);

        // Assert
        assertEquals(expectedOpTable.toString(), actualOpTable.toString());
    }

    @Test
    public void generateOpCodeTable_Relative(){
        // Arrange
        irTest = new ArrayList<>();
        irTest.add(new LineStatement(new Instruction(new Token("lda.i16"), new Token("Msg1"), InstructionType.RELATIVE)));
        irTest.add(new LineStatement(new Instruction(new Token("ldc.i8"), new Token("12"), InstructionType.RELATIVE)));
        sTest = new SymbolTable();
        codeGenTest = new CodeGen(sTest);

        HashMap<Integer, IOpCodeTableElement> expectedOpTable = new HashMap<>();
        expectedOpTable.put(1, new OpCodeTableElement(1, "0000", "D5", 4, "Msg1"));
        expectedOpTable.put(2, new OpCodeTableElement(2, "0003", "D9", 2, null));
        expectedOpTable.get(2).addOperand("0C");

        // Act
        HashMap<Integer, IOpCodeTableElement> actualOpTable = codeGenTest.generateOpCodeTable(irTest);

        // Assert
        assertEquals(expectedOpTable.toString(), actualOpTable.toString());
    }

    @Test
    public void generateOpCodeTable_Immediate(){
        // Arrange
        irTest = new ArrayList<>();
        irTest.add(new LineStatement(new Instruction(new Token("enter.u5"), new Token("16"), InstructionType.IMMEDIATE)));
        irTest.add(new LineStatement(new Instruction(new Token("ldc.i3"), new Token("2"), InstructionType.IMMEDIATE)));
        sTest = new SymbolTable();
        codeGenTest = new CodeGen(sTest);

        HashMap<Integer, IOpCodeTableElement> expectedOpTable = new HashMap<>();
        expectedOpTable.put(1, new OpCodeTableElement(1, "0000", "70", 0, null));
        expectedOpTable.put(2, new OpCodeTableElement(2, "0001", "92", 0, null));

        // Act
        HashMap<Integer, IOpCodeTableElement> actualOpTable = codeGenTest.generateOpCodeTable(irTest);

        // Assert
        assertEquals(expectedOpTable.toString(), actualOpTable.toString());
    }

    @Test
    public void generateOpCodeTable_Inherent() {
        // Arrange
        irTest = new ArrayList<>();
        irTest.add(new LineStatement(new Instruction(new Token("halt"), InstructionType.INHERENT)));
        sTest = new SymbolTable();
        codeGenTest = new CodeGen(sTest);

        HashMap<Integer, IOpCodeTableElement> expectedOpTable = new HashMap<>();
        expectedOpTable.put(1, new OpCodeTableElement(1, "0000", "00", 0, null));

        // Act
        HashMap<Integer, IOpCodeTableElement> actualOpTable = codeGenTest.generateOpCodeTable(irTest);

        // Assert
        assertEquals(expectedOpTable.toString(), actualOpTable.toString());
    }

    @Test
    public void generateOpCodeTable_NoInstruction(){
        // Arrange
        irTest = new ArrayList<>();
        LineStatement l = new LineStatement();
        l.setDirective(new Directive(".cstring","A1"));
        irTest.add(l);
        sTest = new SymbolTable();
        codeGenTest = new CodeGen(sTest);

        HashMap<Integer, IOpCodeTableElement> expectedOpTable = new HashMap<>();
        expectedOpTable.put(1, new OpCodeTableElement(1, "0000", null, 0, null));
        expectedOpTable.get(1).addOperand("41"); expectedOpTable.get(1).addOperand("31"); expectedOpTable.get(1).addOperand("00");


        // Act
        HashMap<Integer, IOpCodeTableElement> actualOpTable = codeGenTest.generateOpCodeTable(irTest);

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
        sTest = new SymbolTable();
        codeGenTest = new CodeGen(sTest);

        HashMap<Integer, IOpCodeTableElement> expectedOpTable = new HashMap<>();
        expectedOpTable.put(1, new OpCodeTableElement(1, "0000", null, 0, null));
        expectedOpTable.put(2, new OpCodeTableElement(2, "0000", "92", 0, null));

        // Act
        HashMap<Integer, IOpCodeTableElement> actualOpTable = codeGenTest.generateOpCodeTable(irTest);

        // Assert
        assertEquals(expectedOpTable.toString(), actualOpTable.toString());
    }


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