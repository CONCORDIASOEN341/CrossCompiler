package com.github.ConcordiaSOEN341.Driver;

public class Driver {
    public static void main(String[] args) {
        String fileName = "src/TestInherentMnemonics.asm";
        new CrossAssembler().assemble(fileName);
    }

}


