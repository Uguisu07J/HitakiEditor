package com.github.uguisu32j.hitakieditor.ui;

public enum EndOfLineChar {
    LF, CRLF;

    public static EndOfLineChar getDefault() {
        EndOfLineChar eolc = switch (System.getProperty("line.separator")) {
            case "\n" -> LF;
            case "\r\n" -> CRLF;
            default -> LF;
        };
        return eolc;
    }
}
