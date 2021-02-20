package com.github.ConcordiaSOEN341.CodeGen;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CodeGenTest extends TestCase {
    final private CodeGen codeGen = new CodeGen();

    @Test
    public void checkHaltInstruction_expectCode00() {
        assertEquals("00", codeGen.getHexCode("halt"));
    }

    @Test
    public void checkPopInstruction_expectCode01() {
        assertEquals("01", codeGen.getHexCode("pop"));
    }

    @Test
    public void checkDupInstruction_expectCode02() {
        assertEquals("02", codeGen.getHexCode("dup"));
    }

    @Test
    public void checkExitInstruction_expectCode03() {
        assertEquals("03", codeGen.getHexCode("exit"));
    }

    @Test
    public void checkRetInstruction_expectCode04() {
        assertEquals("04", codeGen.getHexCode("ret"));
    }

    @Test
    public void checkNotInstruction_expectCode0C() {
        assertEquals("0C", codeGen.getHexCode("not"));
    }

    @Test
    public void checkAndInstruction_expectCode0D() {
        assertEquals("0D", codeGen.getHexCode("and"));
    }

    @Test
    public void checkOrInstruction_expectCode0E() {
        assertEquals("0E", codeGen.getHexCode("or"));
    }

    @Test
    public void checkXorInstruction_expectCode0F() {
        assertEquals("0F", codeGen.getHexCode("xor"));
    }

    @Test
    public void checkNegInstruction_expectCode10() {
        assertEquals("10", codeGen.getHexCode("neg"));
    }

    @Test
    public void checkIncInstruction_expectCode11() {
        assertEquals("11", codeGen.getHexCode("inc"));
    }

    @Test
    public void checkDecInstruction_expectCode12() {
        assertEquals("12", codeGen.getHexCode("dec"));
    }

    @Test
    public void checkAddInstruction_expectCode13() {
        assertEquals("13", codeGen.getHexCode("add"));
    }

    @Test
    public void checkSubInstruction_expectCode14() {
        assertEquals("14", codeGen.getHexCode("sub"));
    }

    @Test
    public void checkMulInstruction_expectCode15() {
        assertEquals("15", codeGen.getHexCode("mul"));
    }

    @Test
    public void checkDivInstruction_expectCode16() {
        assertEquals("16", codeGen.getHexCode("div"));
    }

    @Test
    public void checkRemInstruction_expectCode17() {
        assertEquals("17", codeGen.getHexCode("rem"));
    }

    @Test
    public void checkShlInstruction_expectCode18() {
        assertEquals("18", codeGen.getHexCode("shl"));
    }

    @Test
    public void checkShrInstruction_expectCode19() {
        assertEquals("19", codeGen.getHexCode("shr"));
    }

    @Test
    public void checkTeqInstruction_expectCode1A() {
        assertEquals("1A", codeGen.getHexCode("teq"));
    }

    @Test
    public void checkTneInstruction_expectCode1B() {
        assertEquals("1B", codeGen.getHexCode("tne"));
    }

    @Test
    public void checkTltInstruction_expectCode1C() {
        assertEquals("1C", codeGen.getHexCode("tlt"));
    }

    @Test
    public void checkTgtInstruction_expectCode1D() {
        assertEquals("1D", codeGen.getHexCode("tgt"));
    }

    @Test
    public void checkTleInstruction_expectCode1E() {
        assertEquals("1E", codeGen.getHexCode("tle"));
    }

    @Test
    public void checkTgeInstruction_expectCode1F() {
        assertEquals("1F", codeGen.getHexCode("tge"));
    }

}