package com.github.ConcordiaSOEN341.Parser;

import com.github.ConcordiaSOEN341.Interfaces.ILineStatement;
import com.github.ConcordiaSOEN341.Interfaces.IParser;
import com.github.ConcordiaSOEN341.Interfaces.IPosition;
import com.github.ConcordiaSOEN341.Interfaces.IToken;
import com.github.ConcordiaSOEN341.Lexer.LexerMoq;
import com.github.ConcordiaSOEN341.Lexer.Position;
import com.github.ConcordiaSOEN341.Lexer.Token;
import com.github.ConcordiaSOEN341.Lexer.TokenType;
import com.github.ConcordiaSOEN341.Parser.Instruction;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ParserTest {
    private IParser parser;
    private ArrayList<IToken> tokenList;

    @Test
    public void getAddressingMode_giveToken_expectAddressingModeIsInherent() {
        tokenList = new ArrayList<>();
        tokenList.add(new Token("add", new Position(0, 1, "add".length()), TokenType.MNEMONIC));
        tokenList.add(new Token("~", new Position(0, 2, " ".length()), TokenType.EOL));

        parser = new Parser(new LexerMoq(tokenList));
        InstructionType testType = checkAddressingMode(tokenList.get(0));
        InstructionType expected = InstructionType.INHERENT;
        assertTrue(getInstructionType(testType) == InstructionType.INHERENT);
    }

    @Test
    public void getAddressingMode_giveToken_expectAddressingModeIsImmediate() {
        tokenList = new ArrayList<>();
        tokenList.add(new Token("addv.u3", new Position(0, 1, "add.u5".length()), TokenType.MNEMONIC));
        tokenList.add(new Token("~", new Position(0, 2, " ".length()), TokenType.EOL));

    }

    @Test
    public void getAddressingMode_giveToken_expectAddressingModeIsRelative() {
        tokenList = new ArrayList<>();
        tokenList.add(new Token("addv.i16", new Position(0, 1, "add.i16".length()), TokenType.MNEMONIC));
        tokenList.add(new Token("~", new Position(0, 2, " ".length()), TokenType.EOL));

    }
    /*
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
     */


    @Test
    public void generateIR_whenTokenListEmpty_expectEmptyLineStatementArrayList() {
        tokenList = new ArrayList<>();
        parser = new Parser(new LexerMoq(tokenList));

        ArrayList<ILineStatement> lineStatements = parser.parse();

        assertEquals(0, lineStatements.size());
    }
}