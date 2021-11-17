package com.github.uguisu32j.hitakieditor.ui;

public enum EndOfLine {
	LF, CR_LF, CR;

	public static EndOfLine getDefault() {
		EndOfLine eol = switch (System.getProperty("line.separator")) {
		case "\n" -> LF;
		case "\r" -> CR;
		case "\r\n" -> CR_LF;
		default -> throw new IllegalArgumentException("Unsupported end of line");
		};
		return eol;
	}
}