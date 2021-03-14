package com.github.ConcordiaSOEN341.Error;

public enum ErrorType {
    PARSER_ERROR {
        @Override
        public String toString() {
            return "ParserError";
        }
    },
    SCANNER_ERROR {
        @Override
        public String toString() {
            return "ScannerError";
        }
    }
}
