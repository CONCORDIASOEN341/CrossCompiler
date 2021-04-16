package com.github.ConcordiaSOEN341.CommandHandler;

import com.github.ConcordiaSOEN341.CrossAssembler.CommandHandler;
import org.junit.Assert;
import org.junit.Test;

public class CommandHandlerTest {

    @Test
    public void parseArgs_WhenArgumentsIncludeListing_expectValuesSet(){
        String[] args = {"Test", "123", "-l"};
        CommandHandler commandHandler = new CommandHandler(args);
        Assert.assertTrue(commandHandler.isListing());
        Assert.assertFalse(commandHandler.isVerbose());
        Assert.assertNull(commandHandler.getFile());
    }

    @Test
    public void parseArgs_WhenArgumentsIncludeVerbose_expectValuesSet(){
        String[] args = {"", "", "-v"};
        CommandHandler commandHandler = new CommandHandler(args);
        Assert.assertFalse(commandHandler.isListing());
        Assert.assertTrue(commandHandler.isVerbose());
        Assert.assertNull(commandHandler.getFile());
    }

    @Test
    public void parseArgs_WhenArgumentsIncludeFile_expectFile(){
        String[] args = {"", "", "-v", "-l", "program.asm"};
        CommandHandler commandHandler = new CommandHandler(args);
        Assert.assertTrue(commandHandler.isListing());
        Assert.assertTrue(commandHandler.isVerbose());
        Assert.assertEquals(commandHandler.getFile(), "program.asm");
    }
}