package com.github.ConcordiaSOEN341.Reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Reader {
    private final static int Eof = -1;
    private File file;
    private FileInputStream inputStream;

    public Reader(File file) {
        try {
            inputStream = new FileInputStream(file);
        } catch(FileNotFoundException e) {
            System.out.println("File not found");
        }
    }
    public int getEof() { return Eof; }

    public int read() {
        try {
            return inputStream.read();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

}
