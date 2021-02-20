package com.github.ConcordiaSOEN341.Lexer;

import junit.framework.TestCase;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TokenTest extends TestCase {
    private Token token;

    @Test
    public void createToken_whenArgumentsDefault_expectDefaultFields() {
        token = new Token(null, 0, 0, 0, null);

        assertTrue(StringUtils.isEmpty(token.getTokenString()));
        assertEquals(0, token.getLine());
        assertEquals(0, token.getStartColumn());
        assertEquals(0, token.getEndColumn());
        assertNull(token.getTokenType());
    }

    @Test
    public void createToken_whenArgumentsEmptyString_expectDefaultFields() {
        token = new Token("", 0, 0, 0, null);

        assertTrue(StringUtils.isEmpty(token.getTokenString()));
        assertEquals(0, token.getLine());
        assertEquals(0, token.getStartColumn());
        assertEquals(0, token.getEndColumn());
        assertNull(token.getTokenType());
    }

    @Test
    public void createToken_whenArgumentsNonDefault_expectValuesSet() {
        final String HALT_MNEMONIC = "halt";

        token = new Token(HALT_MNEMONIC, 1, 1, HALT_MNEMONIC.length(), TokenType.MNEMONIC);

        assertEquals("halt", token.getTokenString());
        assertEquals(1, token.getLine());
        assertEquals(1, token.getStartColumn());
        assertEquals(4, token.getEndColumn());
        assertEquals(TokenType.MNEMONIC, token.getTokenType());
    }

    @Test
    public void setLine() {
        String comment = "; bruh";
        token = new Token(comment, 1, 1, comment.length(), TokenType.COMMENT);

        assertEquals(1, token.getLine());

        token.setLine(20);

        assertEquals(20, token.getLine());
    }

    @Test
    public void setStartColumn() {
        String label = "loop";

        token = new Token(label, 1, 1, label.length(), TokenType.LABEL);

        assertEquals(1, token.getStartColumn());

        token.setStartColumn(420);

        assertEquals(420, token.getStartColumn());
    }

    @Test
    public void setEndColumn() {
        String offset = "1";

        token = new Token(offset, 1, 1, offset.length(), TokenType.OFFSET);

        assertEquals(1, token.getEndColumn());

        token.setEndColumn(420);

        assertEquals(420, token.getEndColumn());
    }

    @Test
    public void setTokenString() {
        String cString = "c";

        token = new Token(cString, 1, 1, cString.length(), TokenType.CSTRING);

        assertEquals(cString, token.getTokenString());

        token.setTokenString("yuh");

        assertEquals("yuh", token.getTokenString());
        assertNotSame(cString, token.getTokenString());
    }

    @Test
    public void setTokenType() {
        String EOF = "EOF";

        token = new Token(EOF, 1, 1, EOF.length(), TokenType.EOF);

        assertEquals(TokenType.EOF, token.getTokenType());

        token.setTokenType(TokenType.EOL);

        assertEquals(TokenType.EOL, token.getTokenType());
    }
}