package com.github.ConcordiaSOEN341.CodeGen;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CodeGenTest extends TestCase {
    private CodeGen codeGen = new CodeGen();

    @Test
    public void checkCode00_expectHaltInstruction() {
        assertEquals("halt", codeGen.getInstruction("00"));
    }

    @Test
    public void checkCode01_expectPopInstruction() {
        assertEquals("pop", codeGen.getInstruction("01"));
    }

    @Test
    public void checkCode02_expectDupInstruction() {
        assertEquals("dup", codeGen.getInstruction("02"));
    }

    @Test
    public void checkCode03_expectExitInstruction() {
        assertEquals("exit", codeGen.getInstruction("03"));
    }

    @Test
    public void checkCode04_expectRetInstruction() {
        assertEquals("ret", codeGen.getInstruction("04"));
    }

    @Test
    public void checkCode0C_expectNotInstruction() {
        assertEquals("not", codeGen.getInstruction("0C"));
    }

    @Test
    public void checkCode0D_expectAndInstruction() {
        assertEquals("and", codeGen.getInstruction("0D"));
    }

    @Test
    public void checkCode0E_expectOrInstruction() {
        assertEquals("or", codeGen.getInstruction("0E"));
    }

    @Test
    public void checkCode0F_expectXorInstruction() {
        assertEquals("xor", codeGen.getInstruction("0F"));
    }

    @Test
    public void checkCode10_expectNegInstruction() {
        assertEquals("neg", codeGen.getInstruction("10"));
    }

    @Test
    public void checkCode11_expectIncInstruction() {
        assertEquals("inc", codeGen.getInstruction("11"));
    }

    @Test
    public void checkCode12_expectDecInstruction() {
        assertEquals("dec", codeGen.getInstruction("12"));
    }

    @Test
    public void checkCode13_expectAddInstruction() {
        assertEquals("add", codeGen.getInstruction("13"));
    }

    @Test
    public void checkCode14_expectSubInstruction() {
        assertEquals("sub", codeGen.getInstruction("14"));
    }

    @Test
    public void checkCode15_expectMulInstruction() {
        assertEquals("mul", codeGen.getInstruction("15"));
    }

    @Test
    public void checkCode16_expectDivInstruction() {
        assertEquals("div", codeGen.getInstruction("16"));
    }

    @Test
    public void checkCode17_expectRemInstruction() {
        assertEquals("rem", codeGen.getInstruction("17"));
    }

    @Test
    public void checkCode18_expectShlInstruction() {
        assertEquals("shl", codeGen.getInstruction("18"));
    }

    @Test
    public void checkCode19_expectShrInstruction() {
        assertEquals("shr", codeGen.getInstruction("19"));
    }

    @Test
    public void checkCode1A_expectTeqInstruction() {
        assertEquals("teq", codeGen.getInstruction("1A"));
    }

    @Test
    public void checkCode1B_expectTneInstruction() {
        assertEquals("tne", codeGen.getInstruction("1B"));
    }

    @Test
    public void checkCode1C_expectTltInstruction() {
        assertEquals("tlt", codeGen.getInstruction("1C"));
    }

    @Test
    public void checkCode1D_expectTgtInstruction() {
        assertEquals("tgt", codeGen.getInstruction("1D"));
    }

    @Test
    public void checkCode1E_expectTleInstruction() {
        assertEquals("tle", codeGen.getInstruction("1E"));
    }

    @Test
    public void checkCode1F_expectTgeInstruction() {
        assertEquals("tge", codeGen.getInstruction("1F"));
    }

}