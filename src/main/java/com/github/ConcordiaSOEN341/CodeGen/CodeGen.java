package com.github.ConcordiaSOEN341.CodeGen;

import com.github.ConcordiaSOEN341.Error.IErrorReporter;
import com.github.ConcordiaSOEN341.Logger.ILogger;
import com.github.ConcordiaSOEN341.Logger.LoggerFactory;
import com.github.ConcordiaSOEN341.Logger.LoggerType;
import com.github.ConcordiaSOEN341.Parser.ILineStatement;

import java.io.*;
import java.util.ArrayList;

public class CodeGen implements ICodeGen {
    private final ILogger logger;
    private final IErrorReporter reporter;

    public CodeGen(LoggerFactory lf, IErrorReporter e) {
        logger = lf.getLogger(LoggerType.CODEGEN);
        reporter = e;
    }

    @Override
    public void generateCode(String fileName, ArrayList<ILineStatement> iR, ArrayList<IOpCodeTableElement> opCodeTable){

        logger.log("Checking with reporter for errors...");
        if (reporter.hasErrors()) {
            System.out.println(reporter.report(fileName));
            System.exit(0);
        } else {
            logger.log("Generating executable...");
            generateExe(fileName, opCodeTable);
            logger.log("Executable has been generated");

            if(logger.getHandler().isListing()) {
                logger.log("Generating listing file...");
                generateListingFile(fileName, iR, opCodeTable);
                logger.log("Listing file has been generated");
            }
        }

    }

    @Override
    public void generateExe(String fileName, ArrayList<IOpCodeTableElement> opCodeTable) {
        String listFile = fileName.substring(0, fileName.length() - 4) + ".exe";
        logger.log("Generating Executable file \"" + listFile + "\"");
        try {
            FileOutputStream fStream = new FileOutputStream(listFile);
            DataOutputStream data = new DataOutputStream(fStream);

            logger.log("Generating byte code...");
            for (IOpCodeTableElement oTE : opCodeTable) {
                if (oTE.getOpCode().length() > 0) {
                    data.writeBytes(oTE.getOpCode());
                }
                for (String oP : oTE.getOperands()) {
                    data.writeBytes(oP.substring(0,2));
                    if (oP.length() == 4) {
                        data.writeBytes(oP.substring(2,4));
                    }
                }
            }

            logger.log("Byte code generated");

            fStream.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred");
            System.out.println("The program will terminate.");
            e.printStackTrace();
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void generateListingFile(String fileName, ArrayList<ILineStatement> iR, ArrayList<IOpCodeTableElement> opCodeTable) {
        String listFile = fileName.substring(0, fileName.length() - 4) + ".lst";
        logger.log("Creating listing file \"" + listFile + "\"");
        try {
            FileWriter listingWriter = new FileWriter(listFile);
            String[] byteCode = listingOP(iR, opCodeTable);
            String[] label = listingIRLabel(iR);
            String[] mne = listingIRMne(iR);
            String[] operands = listingIROps(iR);
            String[] comments = listingIRComments(iR);

            for (int i = 0; i < iR.size(); i++) {
                listingWriter.write(byteCode[i]);
                listingWriter.write(label[i]);
                listingWriter.write(mne[i]);
                listingWriter.write(operands[i]);
                listingWriter.write(comments[i]);
            }

            listingWriter.close();
        } catch (IOException e) {
            //TODO: USE ERROR REPORTER HERE
            // THEN LOG ERROR IN ERROR REPORTER
            System.out.println("An error occurred");
            System.out.println("The program will terminate.");
            e.printStackTrace();
            System.exit(0);
        }
    }

    @Override
    public String[] listingOP(ArrayList<ILineStatement> iR, ArrayList<IOpCodeTableElement> opCodeTable) {
        String[] arr = new String[iR.size()];
        StringBuilder[] sbLines = new StringBuilder[iR.size()];

        // Init Header
        sbLines[0] = new StringBuilder();
        sbLines[0].append("Line Addr Code");
        int realHeaderLength = sbLines[0].length() - 2;
        int maxLineLength = realHeaderLength;

        for (int i = 0; i < iR.size() - 1; i++) {
            StringBuilder sb = new StringBuilder();

            sb.append(i + 1).append("\t ").append(opCodeTable.get(i).getAddress()).append(" ");

            if (opCodeTable.get(i).getOpCode().length() > 0) {
                sb.append(opCodeTable.get(i).getOpCode()).append(" ");
            }

            for (String op : opCodeTable.get(i).getOperands()) {
                sb.append(op).append(" ");
            }

            maxLineLength = Math.max(maxLineLength, sb.length());
            sbLines[i + 1] = sb;
        }

        sbLines[0].append(" ".repeat(maxLineLength - realHeaderLength)).append("\t");
        arr[0] = sbLines[0].toString();

        for (int i = 1; i < iR.size(); i++) {
            sbLines[i].append(" ".repeat(maxLineLength - sbLines[i].length())).append("\t");
            arr[i] = sbLines[i].toString();
        }

        return arr;
    }

    @Override
    public String[] listingIRLabel(ArrayList<ILineStatement> iR) {
        String[] arr = new String[iR.size()];
        StringBuilder[] sbLines = new StringBuilder[iR.size()];

        // Init Header
        sbLines[0] = new StringBuilder();
        sbLines[0].append("Label");
        int maxLabelLength = sbLines[0].length();

        for (int i = 0; i < iR.size() - 1; i++) {
            StringBuilder sb = new StringBuilder();

            sb.append(iR.get(i).getLabel().getTokenString());

            maxLabelLength = Math.max(maxLabelLength, sb.length());
            sbLines[i + 1] = sb;
        }

        for (int i = 0; i < iR.size(); i++) {
            sbLines[i].append(" ".repeat(maxLabelLength - sbLines[i].length())).append("\t\t");
            arr[i] = sbLines[i].toString();
        }

        return arr;

    }

    @Override
    public String[] listingIRMne(ArrayList<ILineStatement> iR) {
        String[] arr = new String[iR.size()];
        StringBuilder[] sbLines = new StringBuilder[iR.size()];

        // Init Header
        sbLines[0] = new StringBuilder();
        sbLines[0].append("Mne");
        int maxCoreLength = sbLines[0].length();

        for (int i = 0; i < iR.size() - 1; i++) {
            StringBuilder sb = new StringBuilder();

            if (iR.get(i).getInstruction().getMnemonic().getTokenString().equals("")) {
                sb.append(iR.get(i).getDirective().getDir().getTokenString());
            } else {
                sb.append(iR.get(i).getInstruction().getMnemonic().getTokenString());
            }

            maxCoreLength = Math.max(maxCoreLength, sb.length());
            sbLines[i + 1] = sb;
        }

        for (int i = 0; i < iR.size(); i++) {
            sbLines[i].append(" ".repeat(maxCoreLength - sbLines[i].length())).append("  ");
            arr[i] = sbLines[i].toString();
        }

        return arr;
    }

    @Override
    public String[] listingIROps(ArrayList<ILineStatement> iR) {
        String[] arr = new String[iR.size()];
        StringBuilder[] sbLines = new StringBuilder[iR.size()];

        // Init Header
        sbLines[0] = new StringBuilder();
        sbLines[0].append("Operand");
        int maxCoreLength = sbLines[0].length();

        for (int i = 0; i < iR.size() - 1; i++) {
            StringBuilder sb = new StringBuilder();

            if (iR.get(i).getInstruction().getMnemonic().getTokenString().equals("")) {
                sb.append(iR.get(i).getDirective().getCString().getTokenString());
            } else {
                sb.append(iR.get(i).getInstruction().getOperand().getTokenString());
            }

            maxCoreLength = Math.max(maxCoreLength, sb.length());
            sbLines[i + 1] = sb;
        }

        for (int i = 0; i < iR.size(); i++) {
            sbLines[i].append(" ".repeat(maxCoreLength - sbLines[i].length())).append("\t\t");
            arr[i] = sbLines[i].toString();
        }

        return arr;
    }

    @Override
    public String[] listingIRComments(ArrayList<ILineStatement> iR) {
        String[] arr = new String[iR.size()];
        StringBuilder[] sbLines = new StringBuilder[iR.size()];

        // Init Header
        sbLines[0] = new StringBuilder();
        sbLines[0].append("Comments");
        int maxCommentLength = sbLines[0].length();

        for (int i = 0; i < iR.size() - 1; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(iR.get(i).getComment().getTokenString());

            maxCommentLength = Math.max(maxCommentLength, sb.length());
            sbLines[i + 1] = sb;
        }

        for (int i = 0; i < iR.size(); i++) {
            sbLines[i].append(" ".repeat(maxCommentLength - sbLines[i].length())).append("\t\n");
            arr[i] = sbLines[i].toString();
        }

        return arr;
    }
}
