package com.github.uguisu32j.hitakieditor.ui;

import java.nio.file.Path;

import javax.swing.JTextPane;

@SuppressWarnings("serial")
public class CodePane extends JTextPane {
    private StatusBar statusBar;

    public CodePane(Path path) {
        statusBar = new StatusBar(this);
    }

    public StatusBar getStatusBar() {
        return statusBar;
    }

    public void setLine(int line) {

    }
}
