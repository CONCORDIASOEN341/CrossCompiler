package com.github.ConcordiaSOEN341.Lexer;

import com.github.ConcordiaSOEN341.Error.ErrorType;
import com.github.ConcordiaSOEN341.Interfaces.ILogger;
import com.github.ConcordiaSOEN341.Interfaces.IReader;
import com.github.ConcordiaSOEN341.Logger.LoggerFactory;
import com.github.ConcordiaSOEN341.Logger.LoggerType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LexerFSM {
    private HashMap<Integer, HashMap<Integer, Integer>> transitions;
    private final List<State> states = new ArrayList<>();
    private final IReader reader;

    // ALPHABET TYPES
    private final List<Integer> anyChar = new ArrayList<>();
    private final List<Integer> letters = new ArrayList<>();
    private final List<Integer> nonZero = new ArrayList<>();
    private final List<Integer> whiteSpace = new ArrayList<>();
    private int minus;
    private int zero;
    private int semiColon;
    private int period;
    private int quote;
    private int newline;
    private int tab;
    private int cr;
    private int space;
    private int endOfFile;

    private final ILogger logger;

    public LexerFSM(IReader r, LoggerFactory lf) {
        logger = lf.getLogger(LoggerType.LEXER);
        logger.log("Initializing Lexer Finite State Machine...");
        reader = r;
        initializeAlphabet();
        initializeStates();
        initializeMap();
        logger.log("Initialized");
    }

    private void initializeMap() {
        logger.log("Initializing Map");
        transitions = new HashMap<>();

        transitions.put(states.get(0).getStateID(), new HashMap<>());
        logger.log("Initializing State - Intermediate");
        // STATE 1: INTERMEDIATE
        transitions.put(states.get(1).getStateID(), new HashMap<>());
        transitions.get(1).put(tab, 1);
        transitions.get(1).put(cr, 1);
        transitions.get(1).put(space, 1);
        for (int letter : letters) {
            transitions.get(1).put(letter, 2);
        }
        transitions.get(1).put(semiColon, 4);
        transitions.get(1).put(endOfFile, 6);
        transitions.get(1).put(newline, 7);
        transitions.get(1).put(zero, 8);
        for (int nonZero : nonZero) {
            transitions.get(1).put(nonZero, 9);
        }
        transitions.get(1).put(period, 11);
        transitions.get(1).put(quote, 13);
        transitions.get(1).put(minus, 17);

        logger.log("Initializing State - Identifier");
        // STATE 2: IDENTIFIER
        transitions.put(states.get(2).getStateID(), new HashMap<>());
        for (int letter : letters) {
            transitions.get(2).put(letter, 2);
        }
        transitions.get(2).put(period, 2);
        transitions.get(2).put(zero, 2);
        for (int nonZero : nonZero) {
            transitions.get(2).put(nonZero, 2);
        }
        for (int wp : whiteSpace) {
            transitions.get(2).put(wp, 3);
        }
        transitions.get(2).put(quote, 3);
        transitions.get(2).put(semiColon, 3);
        transitions.get(2).put(newline, 3);
        transitions.get(2).put(endOfFile, 3);

        logger.log("Initializing State - Comments");
        // STATE 4: COMMENTS
        transitions.put(states.get(4).getStateID(), new HashMap<>());
        for (int c : anyChar) {
            transitions.get(4).put(c, 4);
        }
        transitions.get(4).put(newline, 5);
        transitions.get(4).put(endOfFile, 5);

        logger.log("Initializing State - Zero");
        // STATE 8: ZERO
        transitions.put(states.get(8).getStateID(), new HashMap<>());
//        for (int nonZero : nonZero){
//            transitions.get(8).put(nonZero,10);
//        }
        for (int letter : letters) {
            transitions.get(8).put(letter, 10);
        }
        for (int ws : whiteSpace) {
            transitions.get(8).put(ws, 10);
        }
        transitions.get(8).put(semiColon, 10);
        transitions.get(8).put(period, 10);
        transitions.get(8).put(newline, 10);
        transitions.get(8).put(endOfFile, 10);

        logger.log("Initializing State - Number");
        // STATE 9: NUMBERS
        transitions.put(states.get(9).getStateID(), new HashMap<>());
        transitions.get(9).put(zero, 9);
        for (int nonZero : nonZero) {
            transitions.get(9).put(nonZero, 9);
        }
        for (int letter : letters) {
            transitions.get(9).put(letter, 10);
        }
        for (int ws : whiteSpace) {
            transitions.get(9).put(ws, 10);
        }
        transitions.get(9).put(semiColon, 10);
        transitions.get(9).put(period, 10);
        transitions.get(9).put(newline, 10);
        transitions.get(9).put(endOfFile, 10);

        logger.log("Initializing State - Directive");
        // STATE 11: DIRECTIVE
        transitions.put(states.get(11).getStateID(), new HashMap<>());
        transitions.get(11).put(zero, 11);
        for (int nonZero : nonZero) {
            transitions.get(11).put(nonZero, 11);
        }
        for (int letter : letters) {
            transitions.get(11).put(letter, 11);
        }
        for (int ws : whiteSpace) {
            transitions.get(11).put(ws, 12);
        }
        transitions.get(11).put(semiColon, 12);
        transitions.get(11).put(period, 12);
        transitions.get(11).put(newline, 12);
        transitions.get(11).put(endOfFile, 12);

        logger.log("Initializing State - CSTRING");
        // STATE 13: CSTRING
        transitions.put(states.get(13).getStateID(), new HashMap<>());
        for (int c : anyChar) {
            if (c != endOfFile)
                transitions.get(13).put(c, 13);
        }
        transitions.get(13).put(quote, 14);
        transitions.get(13).put(newline, 15);
        transitions.get(13).put(endOfFile, 16);

        logger.log("Initializing State - Negatives");
        // STATE 17: NEGATIVES
        transitions.put(states.get(17).getStateID(), new HashMap<>());
        for (int nonZero : nonZero) {
            transitions.get(17).put(nonZero, 9);
        }
        logger.log("Map Initialization Completed");
    }

    private void initializeAlphabet() {
        logger.log("Initializing Alphabet");
        for (int i = 32; i < 256; i++) {
            anyChar.add(i);
        }
        for (int i = 65; i < 91; i++) {
            letters.add(i);
        }
        for (int i = 97; i < 123; i++) {
            letters.add(i);
        }
        for (int i = 49; i < 58; i++) {
            nonZero.add(i);
        }
        minus = '-';
        zero = '0';
        semiColon = ';';
        period = '.';
        quote = '"';
        newline = '\n';
        tab = '\t';
        cr = '\r';
        space = ' ';
        endOfFile = reader.getEof();

//        whiteSpace.add(newline);
        whiteSpace.add(tab);
        whiteSpace.add(cr);
        whiteSpace.add(space);


    }

    private void initializeStates() {
        logger.log("Initializing Token state types");
        // if a final state is achieved, backtrack
        states.add(new State(0, TokenType.ERROR, false));
        states.add(new State(1, TokenType.START, false));
        states.add(new State(2, TokenType.START, false));
        states.add(new State(3, TokenType.IDENTIFIER, true));
        states.add(new State(4, TokenType.START, false));
        states.add(new State(5, TokenType.COMMENT, true));
        states.add(new State(6, TokenType.EOF, false));
        states.add(new State(7, TokenType.EOL, false));
        states.add(new State(8, TokenType.START, false));
        states.add(new State(9, TokenType.START, false));
        states.add(new State(10, TokenType.OFFSET, true));
        states.add(new State(11, TokenType.START, false));
        states.add(new State(12, TokenType.DIRECTIVE, true));
        states.add(new State(13, TokenType.START, false));
        states.add(new State(14, TokenType.CSTRING, false));
        states.add(new State(15, TokenType.ERROR, true, ErrorType.EOL_FOUND));
        states.add(new State(16, TokenType.ERROR, true, ErrorType.EOF_FOUND));
        states.add(new State(17, TokenType.START, false));
    }

    public boolean isBackTrack(int id) {
        return states.get(id).isBacktrack();
    }

    public int getNextStateID(int id, int ch) {
        return transitions.get(id).getOrDefault(ch, 0);
    }

    public int getInitialStateID() {
        return states.get(1).getStateID();
    }

    public TokenType getStateType(int id) {
        return states.get(id).getType();
    }

    public ErrorType getErrorType(int id) {
        return states.get(id).getErrorType();
    }
}
