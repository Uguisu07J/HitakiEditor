package com.github.uguisu32j.hitakieditor.ui;

import java.nio.charset.Charset;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class StatusBar extends JLabel {
	private int line = 1;
	private int colomn = 1;
	private Charset encoding = Charset.forName("UTF-8");
	private EndOfLine endOfLine = EndOfLine.getDefault();
	private EditorMode mode = EditorMode.NONE;

	public StatusBar() {
		setHorizontalAlignment(JLabel.RIGHT);
		setFocusable(false);
		updateText();
	}

	public void updateText() {
		setText(new StringBuilder().append("行 ").append(line).append("   列 ").append(colomn).append("   ")
				.append(encoding).append("   ").append(endOfLine).append("   ").append(mode).append("   ").toString());
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
		updateText();
	}

	public int getColomn() {
		return colomn;
	}

	public void setColomn(int colomn) {
		this.colomn = colomn;
		updateText();
	}

	public Charset getEncoding() {
		return encoding;
	}

	public void setEncoding(Charset encoding) {
		this.encoding = encoding;
		updateText();
	}

	public EndOfLine getEndOfLine() {
		return endOfLine;
	}

	public void setEndOfLine(EndOfLine endOfLine) {
		this.endOfLine = endOfLine;
		updateText();
	}

	public EditorMode getMode() {
		return mode;
	}

	public void setMode(EditorMode mode) {
		this.mode = mode;
		updateText();
	}
}