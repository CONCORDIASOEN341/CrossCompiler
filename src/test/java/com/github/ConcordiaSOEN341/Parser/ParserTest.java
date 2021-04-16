package com.github.ConcordiaSOEN341.Parser;

import com.github.ConcordiaSOEN341.CodeGen.CodeGen;
import com.github.ConcordiaSOEN341.CodeGen.ICodeGen;
import com.github.ConcordiaSOEN341.CrossAssembler.CommandHandler;
import com.github.ConcordiaSOEN341.Error.ErrorReporter;
import com.github.ConcordiaSOEN341.Error.IErrorReporter;
import com.github.ConcordiaSOEN341.Lexer.*;
import com.github.ConcordiaSOEN341.Logger.LoggerFactory;
import com.github.ConcordiaSOEN341.CodeGen.SymbolTable;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ParserTest {

    private ArrayList<IToken> tokenList;
    private IParser pTest;

    private void init(ArrayList<IToken> input){
        LoggerFactory lFTest = new LoggerFactory(new CommandHandler());
        ParserFSM pFSMTest = new ParserFSM(lFTest);
        SymbolTable sTest = new SymbolTable();
        IErrorReporter eTest = new ErrorReporter(lFTest);
        ILexer lTest = new LexerMoqForParser(input);
        ICodeGen cgTest = new CodeGen(sTest, lFTest, eTest);
        pTest = new Parser(pFSMTest, lTest, cgTest, lFTest, eTest);
    }

    // UHM, even though we just have an end of file, it is still gonna add an empty LineStatement, what is the purpose of this test?
    @Test
    public void parse_giveEmpty_expectEmpty(){
        tokenList = new ArrayList<>();
        tokenList.add(new Token("", new Position(1, 1, 1), TokenType.EOF));

        init(tokenList);
        ArrayList<ILineStatement> lineStatements = pTest.generateIR();

        assertEquals(1, lineStatements.size());

    }

    @Test
    public void parse_giveInherentInstructionType_expectInherentInstructionType(){
        tokenList = new ArrayList<>();
        tokenList.add(new Token("pop", new Position(1, 0, 4), TokenType.MNEMONIC));
        tokenList.add(new Token("", new Position(1, 4, 4), TokenType.EOL));
        tokenList.add(new Token("", new Position(1, 3, 3), TokenType.EOF));

        init(tokenList);
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

        init(tokenList);
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

        init(tokenList);
        ArrayList<ILineStatement> lineStatements = pTest.generateIR();

        assertEquals(lineStatements.get(0).getInstruction().getInstructionType(), InstructionType.RELATIVE);

    }

    @Test
    public void parse_giveListWithLabel_expectSameLabel(){
        tokenList = new ArrayList<>();
        tokenList.add(new Token("Msg1", new Position(1, 0, 4), TokenType.LABEL));
        tokenList.add(new Token("", new Position(1, 9, 9), TokenType.EOL));
        tokenList.add(new Token("", new Position(2, 0, 0), TokenType.EOF));

        init(tokenList);
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

        init(tokenList);
        ArrayList<ILineStatement> lineStatements = pTest.generateIR();

        assertEquals("ABCD1", lineStatements.get(0).getDirective().getCString().getTokenString());

    }

    @Test
    public void parse_giveListWithComment_expectSameComment(){
        tokenList = new ArrayList<>();
        tokenList.add(new Token("A comment", new Position(1, 0, 4), TokenType.COMMENT));
        tokenList.add(new Token("", new Position(1, 4, 4), TokenType.EOL));
        tokenList.add(new Token("", new Position(2, 3, 3), TokenType.EOF));

        init(tokenList);
        ArrayList<ILineStatement> lineStatements = pTest.generateIR();

        assertEquals("A comment", lineStatements.get(0).getComment().getTokenString());

    }
}