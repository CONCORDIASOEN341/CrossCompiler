package com.github.ConcordiaSOEN341.CrossAssembler;

import static java.lang.System.exit;

public class CommandHandler {
    private final String[] args;
    private boolean isListing;
    private boolean isVerbose;
    private String file;

    public CommandHandler() {
        args = new String[0];
        isListing = false;
        isVerbose = false;
        file = "";
    }

    public CommandHandler(String[] args) {
        this.args = args;
        parseArgs();
    }

    private void parseArgs() {
        if (args.length == 0) {
            System.out.println("Intended usage: cma [-h] [-l] [-v] <file>.asm");
            exit(0);
        }
        for (String arg : args) {
            if (arg.equals("-b") || arg.equals("-banner")) {
                System.out.println("Cm Cross-Assembler Version 1.4 - Developed by Team 7.");
                exit(0);
            }
            if (arg.equals("-h") || arg.equals("-help")) {
                System.out.println("Usage: cma [-h] [-l] [-v] <file>.asm");
                exit(0);
            }
            if (arg.equals("-l") || arg.equals("-listing")) {
                isListing = true;
            }
            if (arg.equals("-v") || arg.equals("-verbose")) {
                isVerbose = true;
            }
            if (arg.contains(".asm")) {
                file = arg;
            }
        }
    }

    public String getFile() {
        return file;
    }

    public boolean isVerbose() {
        return isVerbose;
    }

    public boolean isListing() {
        return isListing;
    }
}
