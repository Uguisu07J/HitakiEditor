package com.github.uguisu32j.hitakieditor.ui;

import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import com.github.uguisu32j.hitakieditor.HitakiEditor;

@SuppressWarnings("serial")
public class StatusBar extends JPanel {
    private CodePane codePane;
    private PositionPanel positionPanel = new PositionPanel();
    private DataPanel<Charset> encodingPanel = new DataPanel<>(HitakiEditor.getUIText("encoding"),
            HitakiEditor.getUIText("encoding.select_encoding"), StandardCharsets.UTF_8,
            Charset.availableCharsets().values().toArray(Charset[]::new));
    private DataPanel<EndOfLineSeq> endOfLineCharPanel = new DataPanel<>(HitakiEditor.getUIText("end_of_line_seq"),
            HitakiEditor.getUIText("end_of_line_seq.select_end_of_line_seq"), EndOfLineSeq.getDefault(),
            EndOfLineSeq.values());
    private DataPanel<LangMode> langModePanel = new DataPanel<>(HitakiEditor.getUIText("lang_mode"),
            HitakiEditor.getUIText("lang_mode.select_lang_mode"), LangMode.PLAIN_TEXT, LangMode.values());

    public StatusBar(CodePane codePane) {
        this.codePane = codePane;
        setLayout(new FlowLayout(FlowLayout.RIGHT));
        add(positionPanel);
        add(Box.createGlue());
        add(encodingPanel);
        add(Box.createGlue());
        add(endOfLineCharPanel);
        add(Box.createGlue());
        add(langModePanel);
    }

    public void setPosition(int line, int colomn) {
        positionPanel.setPosition(line, colomn);
    }

    public void setMode(LangMode mode) {
        langModePanel.value = mode;
    }

    public Charset getEncoding() {
        return (Charset) encodingPanel.value;
    }

    public EndOfLineSeq getEndOfLine() {
        return (EndOfLineSeq) endOfLineCharPanel.value;
    }

    private class DataPanel<T> extends JLabel {
        private Object value;

        private DataPanel(String type, String message, T value, T[] selectionValues) {
            super(value.toString());
            this.value = value;
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    Object input = JOptionPane.showInputDialog(null, message, type, JOptionPane.INFORMATION_MESSAGE,
                            null, selectionValues, DataPanel.this.value);
                    if (input != null) {
                        DataPanel.this.value = input;
                        setText(DataPanel.this.value.toString());
                    }
                }
            });
        }
    }

    private class PositionPanel extends JLabel {
        private PositionPanel() {
            super("0 : 0");
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    var line = Integer.parseInt(
                            JOptionPane.showInputDialog(null, HitakiEditor.getUIText("position.type_a_line_number"),
                                    HitakiEditor.getUIText("position.go_to_line"), JOptionPane.INFORMATION_MESSAGE));
                    codePane.setLine(line);
                    setPosition(line, 0);
                }
            });
        }

        private void setPosition(int line, int colomn) {
            setText(line + " : " + colomn);
        }
    }
}
