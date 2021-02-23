package com.github.ConcordiaSOEN341.Reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static java.lang.System.exit;

public class Reader {
    private final static int EOF = -1;
    private final File file;
    private FileInputStream inputStream;

    public Reader(String fileName) {
        file = new File(fileName);

        try {
            inputStream = new FileInputStream(file);
        } catch(FileNotFoundException e) {
            System.out.println("[Error]: File not found");
            exit(0);
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
