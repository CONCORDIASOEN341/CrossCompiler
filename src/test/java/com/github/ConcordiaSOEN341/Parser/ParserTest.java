package com.github.ConcordiaSOEN341.Parser;

import com.github.ConcordiaSOEN341.Error.ErrorReporter;
import com.github.ConcordiaSOEN341.Interfaces.*;
import com.github.ConcordiaSOEN341.Lexer.*;
import com.github.ConcordiaSOEN341.Tables.SymbolTable;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ParserTest {

    private ArrayList<IToken> tokenList;
    private IParser pTest;
    private IErrorReporter eTest;

    private void init(ArrayList<IToken> input){
        SymbolTable sTest = new SymbolTable();
        eTest = new ErrorReporter();
        ILexer lTest = new LexerMoqForParser(input);
        pTest = new Parser(sTest, lTest, eTest);
    }

    @Test
    public void parse_giveEmpty_expectEmpty(){
        tokenList = new ArrayList<>();
        tokenList.add(new Token("", new Position(1, 3, 3), TokenType.EOF));

        init(tokenList);
        ArrayList<ILineStatement> lineStatements = pTest.parse();

        assertEquals(0, lineStatements.size());

    }
    @Test
    public void parse_giveAdd_expectAdd(){
        tokenList = new ArrayList<>();
        tokenList.add(new Token("add", new Position(1, 0, 4), TokenType.MNEMONIC));
        tokenList.add(new Token("", new Position(1, 4, 4), TokenType.EOL));
        tokenList.add(new Token("", new Position(1, 3, 3), TokenType.EOF));

        init(tokenList);
        ArrayList<ILineStatement> lineStatements = pTest.parse();

        assertEquals(lineStatements.get(0).getInstruction().getMnemonic().getTokenString(), "add");

    }

    @Test
    public void parse_giveInherentInstructionType_expectInherentInstructionType(){
        tokenList = new ArrayList<>();
        tokenList.add(new Token("pop", new Position(1, 0, 4), TokenType.MNEMONIC));
        tokenList.add(new Token("", new Position(1, 4, 4), TokenType.EOL));
        tokenList.add(new Token("", new Position(1, 3, 3), TokenType.EOF));

        init(tokenList);
        ArrayList<ILineStatement> lineStatements = pTest.parse();

        assertEquals(lineStatements.get(0).getInstruction().getInstructionType(), InstructionType.INHERENT);

    }

//    @Test
//    public void parse_giveImmediateInstructionType_expectImmediateInstructionType(){
//        tokenList = new ArrayList<>();
//        tokenList.add(new Token("addv.u3", new Position(1, 0, 4), TokenType.MNEMONIC));
//        tokenList.add(new Token("2", new Position(1, 0, 4), TokenType.OFFSET));
//        tokenList.add(new Token("", new Position(1, 4, 4), TokenType.EOL));
//        tokenList.add(new Token("", new Position(1, 3, 3), TokenType.EOF));
//
//        init(tokenList);
//        ArrayList<ILineStatement> lineStatements = pTest.parse();
//
//        assertEquals(lineStatements.get(0).getInstruction().getInstructionType(), InstructionType.IMMEDIATE);
//
//    }
//
//    @Test
//    public void parse_giveError_expectError(){
//        tokenList = new ArrayList<>();
//        tokenList.add(new Token("add.i3", new Position(1, 0, 4), TokenType.MNEMONIC));
//        tokenList.add(new Token("76", new Position(1, 0, 4), TokenType.OFFSET));
//        tokenList.add(new Token("", new Position(1, 4, 4), TokenType.EOL));
//        tokenList.add(new Token("", new Position(1, 3, 3), TokenType.EOF));
//
//        init(tokenList);
//        ArrayList<ILineStatement> lineStatements = pTest.parse();
//
//        assertEquals(1, eTest.getNumberOfErrors());
//
//    }
//
//    @Test
//    public void parse_giveError2_expectError2(){
//        tokenList = new ArrayList<>();
//        tokenList.add(new Token("addv.u3", new Position(1, 0, 4), TokenType.MNEMONIC));
//        tokenList.add(new Token("", new Position(1, 4, 4), TokenType.EOL));
//        tokenList.add(new Token("", new Position(1, 3, 3), TokenType.EOF));
//
//        init(tokenList);
//        ArrayList<ILineStatement> lineStatements = pTest.parse();
//
//        assertEquals(1, eTest.getNumberOfErrors());
//
//    }
//
//    @Test
//    public void parse_giveValid_expectNoError(){
//        tokenList = new ArrayList<>();
//        tokenList.add(new Token("add.i3", new Position(1, 0, 4), TokenType.MNEMONIC));
//        tokenList.add(new Token("2", new Position(1, 0, 4), TokenType.OFFSET));
//        tokenList.add(new Token("", new Position(1, 4, 4), TokenType.EOL));
//        tokenList.add(new Token("", new Position(1, 3, 3), TokenType.EOF));
//
//        init(tokenList);
//        ArrayList<ILineStatement> lineStatements = pTest.parse();
//
//        assertEquals(0, eTest.getNumberOfErrors());
//
//    }
//
//    @Test
//    public void parse_giveListWithDirective_expectSameDirective(){
//        tokenList = new ArrayList<>();
//        tokenList.add(new Token("ABCD1", new Position(1, 0, 4), TokenType.CSTRING));
//        tokenList.add(new Token("", new Position(1, 4, 4), TokenType.EOL));
//        tokenList.add(new Token("", new Position(2, 3, 3), TokenType.EOF));
//
//        init(tokenList);
//        ArrayList<ILineStatement> lineStatements = pTest.parse();
//
//        assertEquals("ABCD1", lineStatements.get(0).getDirective().getDirectiveName());
//
//    }
//
//    @Test
//    public void parse_giveListWithComment_expectSameComment(){
//        tokenList = new ArrayList<>();
//        tokenList.add(new Token("A comment", new Position(1, 0, 4), TokenType.COMMENT));
//        tokenList.add(new Token("", new Position(1, 4, 4), TokenType.EOL));
//        tokenList.add(new Token("", new Position(2, 3, 3), TokenType.EOF));
//
//        init(tokenList);
//        ArrayList<ILineStatement> lineStatements = pTest.parse();
//
//        assertEquals("A comment", lineStatements.get(0).getComment().getTokenString());
//
//    }


        assertEquals(lineStatements.get(0).getInstruction().getInstructionType(), InstructionType.IMMEDIATE);

    }

    @Test
    public void parse_giveError_expectError(){
        tokenList = new ArrayList<>();
        tokenList.add(new Token("add.i3", new Position(1, 0, 4), TokenType.MNEMONIC));
        tokenList.add(new Token("76", new Position(1, 0, 4), TokenType.OFFSET));
        tokenList.add(new Token("", new Position(1, 4, 4), TokenType.EOL));
        tokenList.add(new Token("", new Position(1, 3, 3), TokenType.EOF));

        init(tokenList);
        ArrayList<ILineStatement> lineStatements = pTest.parse();

        assertEquals(1, eTest.getNumberOfErrors());

    }
    @Test
    public void parse_giveError1_expectError(){
        tokenList = new ArrayList<>();
        tokenList.add(new Token("addv.u16", new Position(1, 0, 4), TokenType.MNEMONIC));
        tokenList.add(new Token("760000", new Position(1, 0, 4), TokenType.OFFSET));
        tokenList.add(new Token("", new Position(1, 4, 4), TokenType.EOL));
        tokenList.add(new Token("", new Position(1, 3, 3), TokenType.EOF));

        init(tokenList);
        ArrayList<ILineStatement> lineStatements = pTest.parse();

        assertEquals(1, eTest.getNumberOfErrors());

    }

    @Test
    public void parse_giveError2_expectError2(){
        tokenList = new ArrayList<>();
        tokenList.add(new Token("addv.u3", new Position(1, 0, 4), TokenType.MNEMONIC));
        tokenList.add(new Token("", new Position(1, 4, 4), TokenType.EOL));
        tokenList.add(new Token("", new Position(1, 3, 3), TokenType.EOF));

        init(tokenList);
        ArrayList<ILineStatement> lineStatements = pTest.parse();

        assertEquals(1, eTest.getNumberOfErrors());

    }

    @Test
    public void parse_giveValid_expectNoError(){
        tokenList = new ArrayList<>();
        tokenList.add(new Token("add.i3", new Position(1, 0, 4), TokenType.MNEMONIC));
        tokenList.add(new Token("2", new Position(1, 0, 4), TokenType.OFFSET));
        tokenList.add(new Token("", new Position(1, 4, 4), TokenType.EOL));
        tokenList.add(new Token("", new Position(1, 3, 3), TokenType.EOF));

        init(tokenList);
        ArrayList<ILineStatement> lineStatements = pTest.parse();

        assertEquals(0, eTest.getNumberOfErrors());

    }

    @Test
    public void parse_giveListWithDirective_expectSameDirective(){
        tokenList = new ArrayList<>();
        tokenList.add(new Token("ABCD1", new Position(1, 0, 4), TokenType.CSTRING));
        tokenList.add(new Token("", new Position(1, 4, 4), TokenType.EOL));
        tokenList.add(new Token("", new Position(2, 3, 3), TokenType.EOF));

        init(tokenList);
        ArrayList<ILineStatement> lineStatements = pTest.parse();

        assertEquals("ABCD1", lineStatements.get(0).getDirective().getTokenString());

    }

    @Test
    public void parse_giveListWithComment_expectSameComment(){
        tokenList = new ArrayList<>();
        tokenList.add(new Token("A comment", new Position(1, 0, 4), TokenType.COMMENT));
        tokenList.add(new Token("", new Position(1, 4, 4), TokenType.EOL));
        tokenList.add(new Token("", new Position(2, 3, 3), TokenType.EOF));

        init(tokenList);
        ArrayList<ILineStatement> lineStatements = pTest.parse();

        assertEquals("A comment", lineStatements.get(0).getComment().getTokenString());

    }


}