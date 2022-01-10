package com.github.uguisu32j.hitakieditor.ui;

public enum EndOfLineSeq {
    LF, CRLF;

    public static EndOfLineSeq getDefault() {
        var eols = switch (System.lineSeparator()) {
            case "\n" -> LF;
            case "\r\n" -> CRLF;
            default -> LF;
        };
        return eols;
    }
}
