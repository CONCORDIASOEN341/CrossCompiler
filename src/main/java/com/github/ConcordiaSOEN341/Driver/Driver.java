package com.github.ConcordiaSOEN341.Driver;

import com.github.ConcordiaSOEN341.CommandHandle.CommandHandle;

public class Driver {
    public static void main(String[] args) {
        CommandHandle commandHandle = new CommandHandle(args);
        String filePath = commandHandle.getFile();
        new CrossAssembler().assemble(filePath);
        commandHandle.delete();
        System.out.printf("%04x%n", (int) 'A');
    }

}


