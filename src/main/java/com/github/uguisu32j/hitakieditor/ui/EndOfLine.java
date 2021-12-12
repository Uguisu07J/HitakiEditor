package com.github.uguisu32j.hitakieditor.ui;

public enum EndOfLine {
    LF, CRLF;

    public static EndOfLine getDefault() {
        EndOfLine eol = switch (System.getProperty("line.separator")) {
            case "\n" -> LF;
            case "\r\n" -> CRLF;
            default -> LF;
        };
        return eol;
    }
}
