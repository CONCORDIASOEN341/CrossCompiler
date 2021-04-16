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
            System.out.println("Intended usage: cma [-h] [-l] [-v] File.asm");
            exit(0);
        }
        for (String arg : args) {
            if (arg.equals("-h")) {
                System.out.println("cma [-h] [-l] [-v] File.asm");
                exit(0);
            }
            if (arg.equals("-l")) {
                isListing = true;
            }
            if (arg.equals("-v")) {
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
