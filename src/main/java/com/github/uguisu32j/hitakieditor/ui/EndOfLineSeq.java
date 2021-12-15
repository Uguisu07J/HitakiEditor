package com.github.uguisu32j.hitakieditor.ui;

public enum EndOfLineSeq {
    LF, CRLF;

    public static EndOfLineSeq getDefault() {
        EndOfLineSeq eolc = switch (System.getProperty("line.separator")) {
            case "\n" -> LF;
            case "\r\n" -> CRLF;
            default -> LF;
        };
        return eolc;
    }
}
