package com.github.ConcordiaSOEN341.Parser;

import com.github.ConcordiaSOEN341.Interfaces.ILineStatement;
import com.github.ConcordiaSOEN341.Interfaces.IParser;
import com.github.ConcordiaSOEN341.Interfaces.IToken;
import com.github.ConcordiaSOEN341.Lexer.LexerMoq;
import com.github.ConcordiaSOEN341.Lexer.Position;
import com.github.ConcordiaSOEN341.Lexer.Token;
import com.github.ConcordiaSOEN341.Lexer.TokenType;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ParserTest {
    private IParser parser;
    private ArrayList<IToken> tokenList;

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