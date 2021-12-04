package com.github.uguisu32j.hitakieditor.ui;

import java.nio.file.Path;

import javax.swing.JTextPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class CodePane extends JTextPane {
    private static final Logger LOGGER = LoggerFactory.getLogger(CodePane.class);
    private StatusBar statusBar;
    private Path path;

    public CodePane(Path path) {
        if (LOGGER.isInfoEnabled() && path != null) {
            LOGGER.info("Opening " + path);
        }
        this.path = path;
        statusBar = new StatusBar(this);
    }

    public StatusBar getStatusBar() {
        return statusBar;
    }

    public void setLine(int line) {

    }

    private void open() {

    }

    private void save() {

    }

    public void reopen() {
        save();
        open();
    }
}
