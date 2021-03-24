package com.github.ConcordiaSOEN341.Error;

public enum ErrorType {
    INVALID_CHARACTER("Invalid character."),
    EOF_FOUND("End-of-file in string."),
    EOL_FOUND("End-of-line in string: a carriage return (\\'r') or a line feed ('\\n')."),
    EXTRA_OPERAND("Instructions with inherent mode addressing do not have an operand field."),
    MISSING_OPERAND("This immediate instruction with must have a number as an operand field."),
    // TODO: change '.u5' and such to the actual problematic input, not just the generic one.
    INVALID_UNSIGNED_5BIT_OPERAND("The immediate instruction using '.u5' must have a 5-bit unsigned operand number ranging from 0 to 31."),
    INVALID_SIGNED_3BIT_OPERAND("The immediate instruction using '.i3' must have a 3-bit signed operand number ranging from -4 to 3."),
    INVALID_UNSIGNED_3BIT_OPERAND("The immediate instruction using '.u3' must have a 3-bit unsigned operand number ranging from 0 to 7.");

    private String description;

    private ErrorType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static ErrorType getDefault(){
        return INVALID_CHARACTER;
    }

    @Override
    public String toString() {
        return description;
    }
}
