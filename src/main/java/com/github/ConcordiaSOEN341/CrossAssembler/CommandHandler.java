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
                System.out.println("\u001B[34m" +
                        " ______   _______           _______  _        _______  _______  _______  ______     ______             _________ _______  _______  _______    ______  \n" +
                                "(  __  \\ (  ____ \\|\\     /|(  ____ \\( \\      (  ___  )(  ____ )(  ____ \\(  __  \\   (  ___ \\ |\\     /|  \\__   __/(  ____ \\(  ___  )(       )  / ___  \\ \n" +
                                "| (  \\  )| (    \\/| )   ( || (    \\/| (      | (   ) || (    )|| (    \\/| (  \\  )  | (   ) )( \\   / )     ) (   | (    \\/| (   ) || () () |  \\/   )  )\n" +
                                "| |   ) || (__    | |   | || (__    | |      | |   | || (____)|| (__    | |   ) |  | (__/ /  \\ (_) /      | |   | (__    | (___) || || || |      /  / \n" +
                                "| |   | ||  __)   ( (   ) )|  __)   | |      | |   | ||  _____)|  __)   | |   | |  |  __ (    \\   /       | |   |  __)   |  ___  || |(_)| |     /  /  \n" +
                                "| |   ) || (       \\ \\_/ / | (      | |      | |   | || (      | (      | |   ) |  | (  \\ \\    ) (        | |   | (      | (   ) || |   | |    /  /   \n" +
                                "| (__/  )| (____/\\  \\   /  | (____/\\| (____/\\| (___) || )      | (____/\\| (__/  )  | )___) )   | |        | |   | (____/\\| )   ( || )   ( |   /  /    \n" +
                                "(______/ (_______/   \\_/   (_______/(_______/(_______)|/       (_______/(______/   |/ \\___/    \\_/        )_(   (_______/|/     \\||/     \\|   \\_/  "
                );
                System.out.println("Cm Cross-Assembler - Developed by Team 7.");
                System.out.println("SRC: https://github.com/CONCORDIASOEN341" + "\u001B[0m");
                exit(0);
            }
            if (arg.equals("-h") || arg.equals("-help")) {
                System.out.println("Usage: cma [-h] [-l] [-v] <file>.asm");
                System.out.println("-h writes theses helpful tips");
                System.out.println("-v allows verbose logging useful information for debugging and inspecting");
                System.out.println("-l generates a listing file");
                System.out.println("file.asm is the required path to the file where the binary executable will be generated");
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
