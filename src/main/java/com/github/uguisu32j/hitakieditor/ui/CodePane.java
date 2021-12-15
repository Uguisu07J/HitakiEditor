package com.github.uguisu32j.hitakieditor.ui;

import java.nio.file.Path;

import javax.swing.JOptionPane;
import javax.swing.JTextPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class CodePane extends JTextPane {
    private static final Logger LOGGER = LoggerFactory.getLogger(CodePane.class);
    private StatusBar statusBar;
    private Path path;
    private boolean isModified = false;

    public CodePane(Path path) {
        if (path != null) {
            LOGGER.info("Opening " + path);
        }
        this.path = path;
        statusBar = new StatusBar(this);

        open();
    }

    public StatusBar getStatusBar() {
        return statusBar;
    }

    public void setLine(int line) {

    }

    private void open() {

    }

    public void save() {

        isModified = false;
    }

    public void reopen() {
        if (isModified && JOptionPane.showConfirmDialog(null, "再度開く前に保存しますか？", "再度開く", JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE) == JOptionPane.OK_OPTION) {
            save();
        }
        open();
    }
}
