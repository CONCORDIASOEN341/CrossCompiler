package com.github.ConcordiaSOEN341.Parser;

import com.github.ConcordiaSOEN341.Error.ErrorReporter;
import com.github.ConcordiaSOEN341.Interfaces.ILineStatement;
import com.github.ConcordiaSOEN341.Interfaces.IParser;
import com.github.ConcordiaSOEN341.Interfaces.IToken;
import com.github.ConcordiaSOEN341.Lexer.LexerMoq;
import com.github.ConcordiaSOEN341.Lexer.Position;
import com.github.ConcordiaSOEN341.Lexer.Token;
import com.github.ConcordiaSOEN341.Lexer.TokenType;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ParserTest {
    private IParser parser;
    private ArrayList<IToken> tokenList;

    @Test
    public void getAddressingMode_giveToken_expectAddressingModeIsInherent() {
        tokenList = new ArrayList<>();
        tokenList.add(new Token("add", new Position(0, 1, "add".length()), TokenType.MNEMONIC));
        tokenList.add(new Token("~", new Position(0, 2, "halt".length() + 1), TokenType.EOL));
        parser = new Parser(new LexerMoq(tokenList));

        ArrayList<ILineStatement> lineStatements = parser.parse();
        assertSame(InstructionType.INHERENT, lineStatements.get(0).getInstruction().getInstructionType());
    }

    @Test
    public void getAddressingMode_giveToken_expectAddressingModeIsImmediateNoError() {
        tokenList = new ArrayList<>();
        tokenList.add(new Token("addv.u3", new Position(0, 1, "addv.u3".length()), TokenType.MNEMONIC));
        tokenList.add(new Token("5", new Position(0, 2, "0".length()), TokenType.OFFSET));
        tokenList.add(new Token("~", new Position(0, 2, "halt".length() + 1), TokenType.EOL));
        parser = new Parser(new LexerMoq(tokenList));

        ArrayList<ILineStatement> lineStatements = parser.parse();
        assertSame(InstructionType.IMMEDIATE, lineStatements.get(0).getInstruction().getInstructionType());
    }

    @Test
    public void giveMissingValue_ExpectErrorBack() {
        tokenList = new ArrayList<>();
        tokenList.add(new Token("addv.u3", new Position(0, 1, "addv.u3".length()), TokenType.MNEMONIC));
        tokenList.add(new Token("~", new Position(0, 2, "halt".length() + 1), TokenType.EOL));
        parser = new Parser(new LexerMoq(tokenList));

        ArrayList<ILineStatement> lineStatements = parser.parse();
        assertEquals(1, ErrorReporter.getNumberOfErrors());
    }

    @Test
    public void parse_WhenUnsigned3bitWithOutOfBounds_expectErrorReported() {
        tokenList = new ArrayList<>();
        tokenList.add(new Token("enter.u3", new Position(0, 1, "enter.u3".length()), TokenType.MNEMONIC));
        tokenList.add(new Token("32", new Position(0, 2, "32".length()), TokenType.OFFSET));
        tokenList.add(new Token("~", new Position(0, 3, "halt".length() + 1), TokenType.EOL));
        parser = new Parser(new LexerMoq(tokenList));

        ArrayList<ILineStatement> lineStatements = parser.parse();
        assertEquals(2, ErrorReporter.getNumberOfErrors());
    }


    @Test
    public void getDirective_giveArrayList_expectTheSameDirective() {
        tokenList = new ArrayList<>();
        tokenList.add(new Token("addv.i8", new Position(0, 1, "addv.i8".length()), TokenType.MNEMONIC));
        tokenList.add(new Token("5", new Position(0, 2, "5".length()), TokenType.OFFSET));
        tokenList.add(new Token("ABCD", new Position(0, 2, "ABCD".length()), TokenType.CSTRING));
        tokenList.add(new Token("~", new Position(0, 3, "halt".length() + 1), TokenType.EOL));
        parser = new Parser(new LexerMoq(tokenList));

        ArrayList<ILineStatement> lineStatements = parser.parse();
        assertSame("ABCD", lineStatements.get(0).getDirective().getTokenString());
    }

    @Test
    public void getComment_giveArrayList_expectTheSameComment() {
        tokenList = new ArrayList<>();
        tokenList.add(new Token("addv.i8", new Position(0, 1, "addv.i8".length()), TokenType.MNEMONIC));
        tokenList.add(new Token("5", new Position(0, 2, "5".length()), TokenType.OFFSET));
        tokenList.add(new Token("ABCD", new Position(0, 2, "5".length()), TokenType.CSTRING));
        tokenList.add(new Token("A comment", new Position(0, 2, "5".length()), TokenType.COMMENT));
        tokenList.add(new Token("~", new Position(0, 3, "halt".length() + 1), TokenType.EOL));
        parser = new Parser(new LexerMoq(tokenList));

        ArrayList<ILineStatement> lineStatements = parser.parse();
        assertSame("A comment", lineStatements.get(0).getComment().getTokenString());
    }

    @Test
    public void generateIR_withTokenList_expectLineStatement() {
        tokenList = new ArrayList<>();
        tokenList.add(new Token("enter.u5", new Position(0, 1, "enter.u5".length()), TokenType.MNEMONIC));
        tokenList.add(new Token("7", new Position(0, 1, "7".length()), TokenType.OFFSET));
        tokenList.add(new Token(";test", new Position(0, 2, ";test".length()), TokenType.COMMENT));
        tokenList.add(new Token("~", new Position(0, 2, "halt".length() + 1), TokenType.EOL));

        parser = new Parser(new LexerMoq(tokenList));

        ArrayList<ILineStatement> lineStatements = parser.parse();

        assertEquals(1, lineStatements.size());
        assertEquals(tokenList.get(0).getTokenString(), lineStatements.get(0).getInstruction().getMnemonic().getTokenString());
        assertEquals(tokenList.get(0).getTokenString(), "enter.u5");
    }

    @Test
    public void generateIR_whenTokenListSize2_expect1LineStatement() {
        tokenList = new ArrayList<>();
        tokenList.add(new Token("halt", new Position(0, 1, "halt".length()), TokenType.MNEMONIC));
        tokenList.add(new Token("~", new Position(0, 2, "halt".length() + 1), TokenType.EOL));

        parser = new Parser(new LexerMoq(tokenList));

        ArrayList<ILineStatement> lineStatements = parser.parse();

        assertEquals(1, lineStatements.size());
        assertEquals(tokenList.get(0).getTokenString(), lineStatements.get(0).getInstruction().getMnemonic().getTokenString());
    }


    @Test
    public void generateIR_whenTokenListEmpty_expectEmptyLineStatementArrayList() {
        tokenList = new ArrayList<>();
        parser = new Parser(new LexerMoq(tokenList));

        ArrayList<ILineStatement> lineStatements = parser.parse();

        assertEquals(0, lineStatements.size());
    }
}