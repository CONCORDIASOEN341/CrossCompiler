; rela01.asm
      lda.i16     Msg1
      ldc.i8      12
      lda.i16     Msg2
Msg1  .cstring    "A1"     ; OK. Code generated -> 41 31 00
Msg2  .cstring    "B23"    ; Only ASCII printable characters are allowed.
