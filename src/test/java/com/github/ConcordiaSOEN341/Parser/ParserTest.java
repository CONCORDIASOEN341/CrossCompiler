package com.github.ConcordiaSOEN341.Parser;

import com.github.ConcordiaSOEN341.Lexer.Token;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(MockitoJUnitRunner.class)
public class ParserTest {
    private List<Token> tokenList;

    @Test
    public void createParser_whenListNull_expectListSetNull() {
        Parser parser = new Parser(null);

        assertNull(parser.getTokenList());
    }

    @Test
    public void createParser_whenListNonNull_expectListSet() {
        tokenList = new ArrayList<>();
        Parser parser = new Parser(tokenList);

        assertEquals(tokenList, parser.getTokenList());
    }

    @Test
    public void setTokenList_whenListNull_expectNullList() {
        tokenList = new ArrayList<>();
        Parser parser = new Parser(tokenList);

        parser.setTokenList(null);

        assertNull(parser.getTokenList());
    }

    @Test
    public void setTokenList_whenListNotNull_expectListSet() {
        tokenList = new ArrayList<>();

        Parser parser = new Parser(new ArrayList<>());

        parser.setTokenList(tokenList);

        assertEquals(tokenList, parser.getTokenList());
    }

    @Test
    public void setTokenList_whenListOneElement_expectListSet() {
        tokenList = new ArrayList<>();
        tokenList.add(new Token(null, 0, 0, 0, null));

        Parser parser = new Parser(new ArrayList<>());

        parser.setTokenList(tokenList);

        assertEquals(tokenList, parser.getTokenList());
        assertEquals(1, parser.getTokenList().size());
    }
}