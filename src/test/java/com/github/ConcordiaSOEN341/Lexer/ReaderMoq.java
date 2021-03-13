package com.github.ConcordiaSOEN341.Lexer;

import com.github.ConcordiaSOEN341.Interfaces.IReader;

import java.util.ArrayList;
import java.util.Iterator;

public class ReaderMoq implements IReader {
    private final static char EOF = '~';
    private final Iterator<Character> it;

    public ReaderMoq(ArrayList<Character> file) {
        it = file.iterator();

    }
    public int getEof() { return EOF; }

    public int read() {
        return it.next();
    }
}
