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
        token = new Token(null, new Position(0, 0, 0), null);

        assertTrue(StringUtils.isEmpty(token.getTokenString()));
        assertEquals(0, token.getPosition().getLine());
        assertEquals(0, token.getPosition().getStartColumn());
        assertEquals(0, token.getPosition().getEndColumn());
        assertNull(token.getTokenType());
    }

    @Test
    public void createToken_whenArgumentsEmptyString_expectDefaultFields() {
        token = new Token("", new Position(0, 0, 0), null);

        assertTrue(StringUtils.isEmpty(token.getTokenString()));
        assertEquals(0, token.getPosition().getLine());
        assertEquals(0, token.getPosition().getStartColumn());
        assertEquals(0, token.getPosition().getEndColumn());
        assertNull(token.getTokenType());
    }

    @Test
    public void createToken_whenArgumentsNonDefault_expectValuesSet() {
        final String HALT_MNEMONIC = "halt";

        token = new Token(HALT_MNEMONIC, new Position(1, 1, HALT_MNEMONIC.length()), TokenType.MNEMONIC);

        assertEquals("halt", token.getTokenString());
        assertEquals(1, token.getPosition().getLine());
        assertEquals(1, token.getPosition().getStartColumn());
        assertEquals(4, token.getPosition().getEndColumn());
        assertEquals(TokenType.MNEMONIC, token.getTokenType());
    }

    @Test
    public void setLine() {
        String comment = "; bruh";
        token = new Token(comment, new Position(1, 1, comment.length()), TokenType.COMMENT);

        assertEquals(1, token.getPosition().getLine());

        token.getPosition().setLine(20);

        assertEquals(20, token.getPosition().getLine());
    }

    @Test
    public void setStartColumn() {
        String label = "loop";

        token = new Token(label, new Position(1, 1, label.length()), TokenType.LABEL);

        assertEquals(1, token.getPosition().getStartColumn());

        token.getPosition().setStartColumn(420);

        assertEquals(420, token.getPosition().getStartColumn());
    }

    @Test
    public void setEndColumn() {
        String offset = "1";

        token = new Token(offset, new Position(1, 1, offset.length()), TokenType.OFFSET);

        assertEquals(1, token.getPosition().getEndColumn());

        token.getPosition().setEndColumn(420);

        assertEquals(420, token.getPosition().getEndColumn());
    }

    @Test
    public void setTokenString() {
        String cString = "c";

        token = new Token(cString, new Position(1, 1, cString.length()), TokenType.CSTRING);

        assertEquals(cString, token.getTokenString());

        token.setTokenString("yuh");

        assertEquals("yuh", token.getTokenString());
        assertNotSame(cString, token.getTokenString());
    }

    @Test
    public void setTokenType() {
        String EOF = "EOF";

        token = new Token(EOF, new Position(1, 1, EOF.length()), TokenType.EOF);

        assertEquals(TokenType.EOF, token.getTokenType());

        token.setTokenType(TokenType.EOL);

        assertEquals(TokenType.EOL, token.getTokenType());
    }
}