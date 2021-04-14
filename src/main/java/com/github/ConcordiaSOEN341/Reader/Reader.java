package com.github.ConcordiaSOEN341.Reader;

import com.github.ConcordiaSOEN341.Interfaces.ILogger;
import com.github.ConcordiaSOEN341.Interfaces.IReader;
import com.github.ConcordiaSOEN341.Logger.LoggerFactory;
import com.github.ConcordiaSOEN341.Logger.LoggerType;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Reader implements IReader {
    private final static int EOF = -1;
    private FileInputStream inputStream;

    private final ILogger logger = LoggerFactory.getLogger(LoggerType.READER);

    public Reader(String fileName) {
        File file = new File(fileName);

        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(0);
        }
        logger.log("Created file \"" + fileName + "\"");
    }

    public final int getEof() {
        return EOF;
    }

    public int read() {
        logger.log("Reading file stream...");
        try {
            return inputStream.read();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

}
