package com.github.ConcordiaSOEN341.Maps;

import com.github.ConcordiaSOEN341.CodeGen.CodeGen;
import com.github.ConcordiaSOEN341.Interfaces.ICodeGen;
import com.github.ConcordiaSOEN341.Interfaces.IInstruction;
import com.github.ConcordiaSOEN341.Interfaces.ILineStatement;
import com.github.ConcordiaSOEN341.Interfaces.IToken;
import com.github.ConcordiaSOEN341.Lexer.Token;
import com.github.ConcordiaSOEN341.Lexer.TokenType;
import com.github.ConcordiaSOEN341.Parser.Instruction;
import com.github.ConcordiaSOEN341.Parser.LineStatement;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class CodeGenTest extends TestCase {


    @Test
    public void listingstesting(){
        String testToken1 = "halt";
        String testToken2 = "or";
        String testToken3 = "EOL";

        IToken t1 = new Token(testToken1,1, 1, testToken1.length(), TokenType.MNEMONIC);
        IToken t2 = new Token(testToken2,1, 1, testToken2.length(), TokenType.MNEMONIC);
        IToken t3 = new Token(testToken3,1, 1, testToken3.length(), TokenType.EOL);

        IInstruction I1 = new Instruction(t1);
        IInstruction I2 = new Instruction(t2);

        ArrayList<IToken> t = new ArrayList<>();
        t.add(t1);
        t.add(t2);
        t.add(t3);
        ILineStatement l1 = new LineStatement(I1,t3);
        ILineStatement l2 = new LineStatement(I2,t3);

        ArrayList<ILineStatement> ir = new ArrayList<>();
        ir.add(0,l1);
        ir.add(1,l2);

        ICodeGen cg = new CodeGen();

        String[] test1 = cg.listing(ir);

        assertEquals("1\t 0000 00 \t\t\t  \t\t\t  halt \t\t \t\t\t \t\n",test1[0].toString());
        assertEquals("2\t 0001 0E \t\t\t  \t\t\t  or \t\t \t\t\t \t\n",test1[1].toString());
    }


}