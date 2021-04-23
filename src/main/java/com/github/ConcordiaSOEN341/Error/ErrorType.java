package com.github.ConcordiaSOEN341.Error;

public enum ErrorType {
    INVALID_CHARACTER("Invalid character."),
    EOF_FOUND("End-of-file in string."),
    EOL_FOUND("End-of-line in string: a carriage return (\\'r') or a line feed ('\\n')."),
    SYNTAX_ERROR("Invalid Cm Syntax... Either too many labels or additional token found after instruction."),
    EXTRA_OPERAND("Instructions with inherent mode addressing do not have an operand field."),
    MISSING_OPERAND("This instruction is missing an operand field."),
    INVALID_UNSIGNED_4BIT_OPERAND("The immediate instruction using '.u4' must have a 4-bit unsigned operand number ranging from 0 to 15."),
    INVALID_UNSIGNED_5BIT_OPERAND("The immediate instruction using '.u5' must have a 5-bit unsigned operand number ranging from 0 to 31."),
    INVALID_UNSIGNED_8BIT_OPERAND("The immediate instruction using '.u8' must have a 8-bit unsigned operand number ranging from 0 to 255."),
    INVALID_UNSIGNED_16BIT_OPERAND("The immediate instruction using '.u16' must have a 16-bit unsigned operand number ranging from 0 to 65535."),
    INVALID_SIGNED_3BIT_OPERAND("The immediate instruction using '.i3' must have a 3-bit signed operand number ranging from -4 to 3."),
    INVALID_SIGNED_4BIT_OPERAND("The immediate instruction using '.i4' must have a 4-bit signed operand number ranging from -8 to 7."),
    INVALID_SIGNED_5BIT_OPERAND("The immediate instruction using '.i5' must have a 5-bit signed operand number ranging from -16 to 15."),
    INVALID_SIGNED_8BIT_OPERAND("The immediate instruction using '.i8' must have a 8-bit signed operand number ranging from -128 to 127."),
    INVALID_UNSIGNED_3BIT_OPERAND("The immediate instruction using '.u3' must have a 3-bit unsigned operand number ranging from 0 to 7."),
    LABEL_NOT_FOUND("label not found (or defined)."),
    LABEL_DEFINED("label already defined."),
    INVALID_OPERAND_LABEL_NOT_USED("Operand must refer to a label."),
    INVALID_DIRECTIVE("is an invalid directive. Only .cstring allowed."),
    CSTRING_NOT_FOUND("Directive used without cstring operand.");

    private final String description;

    ErrorType(String description) {
        this.description = description;
    }

    public static ErrorType getDefault() {
        return INVALID_CHARACTER;
    }

    @Override
    public String toString() {
        return description;
    }
}
