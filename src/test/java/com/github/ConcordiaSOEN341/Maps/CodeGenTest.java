package com.github.ConcordiaSOEN341.Maps;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CodeGenTest extends TestCase {
    final private CodeMap codeGen = new CodeMap();

    @Test
    public void checkHaltInstruction_expectCode00() {
        assertEquals("00", codeGen.getValue("halt"));
    }

    @Test
    public void checkPopInstruction_expectCode01() {
        assertEquals("01", codeGen.getValue("pop"));
    }

    @Test
    public void checkDupInstruction_expectCode02() {
        assertEquals("02", codeGen.getValue("dup"));
    }

    @Test
    public void checkExitInstruction_expectCode03() {
        assertEquals("03", codeGen.getValue("exit"));
    }

    @Test
    public void checkRetInstruction_expectCode04() {
        assertEquals("04", codeGen.getValue("ret"));
    }

    @Test
    public void checkNotInstruction_expectCode0C() {
        assertEquals("0C", codeGen.getValue("not"));
    }

    @Test
    public void checkAndInstruction_expectCode0D() {
        assertEquals("0D", codeGen.getValue("and"));
    }

    @Test
    public void checkOrInstruction_expectCode0E() {
        assertEquals("0E", codeGen.getValue("or"));
    }

    @Test
    public void checkXorInstruction_expectCode0F() {
        assertEquals("0F", codeGen.getValue("xor"));
    }

    @Test
    public void checkNegInstruction_expectCode10() {
        assertEquals("10", codeGen.getValue("neg"));
    }

    @Test
    public void checkIncInstruction_expectCode11() {
        assertEquals("11", codeGen.getValue("inc"));
    }

    @Test
    public void checkDecInstruction_expectCode12() {
        assertEquals("12", codeGen.getValue("dec"));
    }

    @Test
    public void checkAddInstruction_expectCode13() {
        assertEquals("13", codeGen.getValue("add"));
    }

    @Test
    public void checkSubInstruction_expectCode14() {
        assertEquals("14", codeGen.getValue("sub"));
    }

    @Test
    public void checkMulInstruction_expectCode15() {
        assertEquals("15", codeGen.getValue("mul"));
    }

    @Test
    public void checkDivInstruction_expectCode16() {
        assertEquals("16", codeGen.getValue("div"));
    }

    @Test
    public void checkRemInstruction_expectCode17() {
        assertEquals("17", codeGen.getValue("rem"));
    }

    @Test
    public void checkShlInstruction_expectCode18() {
        assertEquals("18", codeGen.getValue("shl"));
    }

    @Test
    public void checkShrInstruction_expectCode19() {
        assertEquals("19", codeGen.getValue("shr"));
    }

    @Test
    public void checkTeqInstruction_expectCode1A() {
        assertEquals("1A", codeGen.getValue("teq"));
    }

    @Test
    public void checkTneInstruction_expectCode1B() {
        assertEquals("1B", codeGen.getValue("tne"));
    }

    @Test
    public void checkTltInstruction_expectCode1C() {
        assertEquals("1C", codeGen.getValue("tlt"));
    }

    @Test
    public void checkTgtInstruction_expectCode1D() {
        assertEquals("1D", codeGen.getValue("tgt"));
    }

    @Test
    public void checkTleInstruction_expectCode1E() {
        assertEquals("1E", codeGen.getValue("tle"));
    }

    @Test
    public void checkTgeInstruction_expectCode1F() {
        assertEquals("1F", codeGen.getValue("tge"));
    }

}