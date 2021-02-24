package com.github.ConcordiaSOEN341.Lexer;

import com.github.ConcordiaSOEN341.Reader.IReader;

import java.util.ArrayList;
import java.util.Iterator;

public class ReaderTest implements IReader {
    private final static char EOF = '~';
    private final Iterator<Character> it;

    public ReaderTest(ArrayList<Character> file) {
        it = file.iterator();

    }
    public int getEof() { return EOF; }

    public int read() {
        return it.next();
    }
}
