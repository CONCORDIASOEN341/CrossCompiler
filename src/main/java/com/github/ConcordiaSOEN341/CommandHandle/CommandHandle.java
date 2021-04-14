package com.github.ConcordiaSOEN341.CommandHandle;

import static java.lang.System.exit;

public class CommandHandle {

    private static CommandHandle commandHandle;
    public String[] args;
    private boolean isListing;
    private boolean isVerbose;
    private String file;

    public CommandHandle(String[] args){
        this.args = args;
        parseArgs();
        if(commandHandle == null){
            commandHandle = this;
        }
    }

    public static CommandHandle getInstance(){
        return commandHandle;
    }

    private void parseArgs(){
        if(args.length == 0){
            System.out.println("Intended usage: cma [-h] [-l] [-v] File.asm");
            exit(0);
        }
        for(String arg : args){
            if(arg.equals("-h")){
                System.out.println("cma [-h] [-l] [-v] File.asm");
                exit(0);
            }
            if(arg.equals("-l")){
                //TODO: Toggle Listing File
                isListing = true;
            }
            if(arg.equals("-v")){
                //TODO: Toggle Verbose
                isVerbose = true;
            }
            if(arg.contains(".asm")){
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

    public void delete()
    {
        commandHandle = null;
    }
}
