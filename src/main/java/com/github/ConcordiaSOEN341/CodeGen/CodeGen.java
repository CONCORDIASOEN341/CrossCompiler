package com.github.ConcordiaSOEN341.CodeGen;

import com.github.ConcordiaSOEN341.Error.ErrorReporter;
import com.github.ConcordiaSOEN341.Interfaces.ICodeGen;
import com.github.ConcordiaSOEN341.Interfaces.ILineStatement;
import com.github.ConcordiaSOEN341.Maps.CodeMap;
import org.apache.commons.lang3.StringUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CodeGen implements ICodeGen {

    public void generateListingFile(String fileName, ArrayList<ILineStatement> ir) {
        if (ErrorReporter.hasErrors()) {
            System.out.println(ErrorReporter.report(fileName));
            System.exit(0);
        } else {
            String listFile = fileName.substring(0, fileName.length() - 4) + ".lst";
            try {
                FileWriter listingWriter = new FileWriter(listFile);
                listingWriter.write("Line Addr Code \t\t\tLabel \t\t  Mne \t\tOperand \t\tComments\n");

                String[] listings = listing(ir);

                for (String listing : listings) {
                    listingWriter.write(listing);
                }

                listingWriter.close();
            } catch (IOException e) {
                System.out.println("An error occurred");
                System.out.println("The program will terminate.");
                e.printStackTrace();
                System.exit(0);
            }
        }
    }

    public String[] listing(ArrayList<ILineStatement> ir) {
        CodeMap codeGen = new CodeMap();
        String[] listings = new String[ir.size()];
        String hexAddress = "0000";
        String offset = "";
        String comment = "";
        int skips = 0;

        for (int i = 0; i < ir.size(); i++) {

            if (StringUtils.isEmpty(ir.get(i).getInstruction().toString()) && i > 2) {
                skips++;
                hexAddress = String.format("%04X", i + 1 - skips);
            } else if (StringUtils.isEmpty(ir.get(i).getInstruction().toString())) {
                skips++;
            } else {
                hexAddress = String.format("%04X", i - skips);
            }

            String codeMnemonic = "";

            String mnemonic = ir.get(i).getInstruction().toString();
            offset = ir.get(i).getInstruction().getOffset().getTokenString();
            comment = ir.get(i).getComment().getTokenString();

            if (StringUtils.isNotEmpty(ir.get(i).getInstruction().getOffset().getTokenString())) {
                codeMnemonic = codeGen.determineOpCode(mnemonic, offset);
            }


            listings[i] = ((i + 1) + "\t " + hexAddress + " " + codeMnemonic + " \t\t\t  \t\t\t  " + mnemonic + " \t " + offset + "\t\t\t\t" + comment + " \t\n");
        }
        return listings;
    }


}
