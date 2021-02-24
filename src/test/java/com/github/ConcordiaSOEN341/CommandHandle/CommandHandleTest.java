package com.github.ConcordiaSOEN341.CommandHandle;

import org.junit.Assert;
import org.junit.Test;

public class CommandHandleTest {

    @Test
    public void parseArgs_WhenArgumentsIncludeListing_expectValuesSet(){
        String[] args = {"Test", "123", "-l"};
        CommandHandle commandHandle = new CommandHandle(args);
        Assert.assertTrue(CommandHandle.getInstance().isListing());
        Assert.assertFalse(CommandHandle.getInstance().isVerbose());
        Assert.assertNull(commandHandle.getFile());
    }

    @Test
    public void parseArgs_WhenArgumentsIncludeVerbose_expectValuesSet(){
        String[] args = {"", "", "-v"};
        CommandHandle commandHandle = new CommandHandle(args);
        Assert.assertFalse(CommandHandle.getInstance().isListing());
        Assert.assertTrue(CommandHandle.getInstance().isVerbose());
        Assert.assertNull(commandHandle.getFile());
    }

    @Test
    public void parseArgs_WhenArgumentsIncludeFile_expectFile(){
        String[] args = {"", "", "-v", "-l", "program.asm"};
        CommandHandle commandHandle = new CommandHandle(args);
        Assert.assertTrue(CommandHandle.getInstance().isListing());
        Assert.assertTrue(CommandHandle.getInstance().isVerbose());
        Assert.assertEquals(commandHandle.getFile(), "program.asm");
    }
    @Test
    public void getInstance_WhenCreatingSecondCommandHandler_expectSameInstanceAsFirst(){
        String[] args = {"Test.asm", "-l"};
        CommandHandle commandHandle1 = new CommandHandle(args);

        Assert.assertSame(CommandHandle.getInstance(), commandHandle1);
    }
}