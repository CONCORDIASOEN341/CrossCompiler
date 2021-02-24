package com.github.ConcordiaSOEN341.Reader;

import com.github.ConcordiaSOEN341.Interfaces.IReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Reader implements IReader {
    private final static int EOF = -1;
    private final File file;
    private FileInputStream inputStream;

    public Reader(String fileName) {
        file = new File(fileName);

        try {
            inputStream = new FileInputStream(file);
        } catch(FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(0);
        }
    }
    public int getEof() { return EOF; }

    public int read() {
        try {
            return inputStream.read();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

}
