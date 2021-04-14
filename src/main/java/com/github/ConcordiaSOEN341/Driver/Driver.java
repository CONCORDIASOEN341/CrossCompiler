package com.github.ConcordiaSOEN341.Driver;

public class Driver {
    public static void main(String[] args) {
        String fileName = "Examples_relative/rela01.asm";
        new CrossAssembler().assemble(fileName);
    }

}


