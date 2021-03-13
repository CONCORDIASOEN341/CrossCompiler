package com.github.ConcordiaSOEN341.Parser;

import com.github.ConcordiaSOEN341.Interfaces.ILineStatement;
import com.github.ConcordiaSOEN341.Interfaces.IToken;
import com.github.ConcordiaSOEN341.Lexer.Position;
import com.github.ConcordiaSOEN341.Lexer.Token;
import com.github.ConcordiaSOEN341.Lexer.TokenType;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ParserTest {
    private Parser parser;
    private ArrayList<IToken> tokenList;

    @Test
    public void generateIR_whenTokenListSize2_expect1LineStatement() {
        parser = new Parser();
        tokenList = new ArrayList<>();

        IToken mnemonic = new Token("halt", new Position(0, 1, "halt".length()), TokenType.MNEMONIC);
        IToken eol = new Token("~", new Position(0, mnemonic.getEndColumn() + 1, mnemonic.getEndColumn() + 1), TokenType.EOL);

        tokenList.add(mnemonic);
        tokenList.add(eol);

        ArrayList<ILineStatement> lineStatements = parser.generateIR(tokenList);

        assertEquals(1, lineStatements.size());
        assertEquals(mnemonic.getTokenString(), lineStatements.iterator().next().getInstruction().getMnemonic().getTokenString());
    }

    @Test
    public void generateIR_whenTokenListEmpty_expectEmptyLineStatementArrayList() {
        parser = new Parser();
        tokenList = new ArrayList<>();

        ArrayList<ILineStatement> lineStatements = parser.generateIR(tokenList);

        assertEquals(0, lineStatements.size());
    }
}