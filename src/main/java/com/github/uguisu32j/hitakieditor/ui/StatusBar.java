package com.github.uguisu32j.hitakieditor.ui;

import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.charset.Charset;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.github.uguisu32j.hitakieditor.HitakiEditor;

@SuppressWarnings("serial")
public class StatusBar extends JPanel {
    private CodePane editorPane;
    private PositionPanel positionPanel = new PositionPanel();
    private DataPanel<Charset> encodingPanel = new DataPanel<>(HitakiEditor.getUIString("encoding"),
            HitakiEditor.getUIString("encoding.select_encoding"), Charset.forName("UTF-8"),
            Charset.availableCharsets().values().toArray(Charset[]::new));
    private DataPanel<EndOfLineChar> endOfLineCharPanel = new DataPanel<>(HitakiEditor.getUIString("end_of_line_char"),
            HitakiEditor.getUIString("end_of_line_char.select_end_of_line_char"), EndOfLineChar.getDefault(),
            EndOfLineChar.values());
    private DataPanel<LangMode> langModePanel = new DataPanel<>(HitakiEditor.getUIString("lang_mode"),
            HitakiEditor.getUIString("lang_mode.select_lang_mode"), LangMode.PLAIN, LangMode.values());

    public StatusBar(CodePane editorPane) {
        this.editorPane = editorPane;
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
        positionPanel.line = line;
        positionPanel.colomn = colomn;
        positionPanel.updateData();
    }

    public void setMode(LangMode mode) {
        langModePanel.data = mode;
    }

    public Charset getEncoding() {
        return (Charset) encodingPanel.data;
    }

    public EndOfLineChar getEndOfLine() {
        return (EndOfLineChar) endOfLineCharPanel.data;
    }

    private class DataPanel<T> extends JLabel {
        private Object data;

        private DataPanel(String type, String message, T data, T[] list) {
            super(data.toString());
            this.data = data;
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    Object obj = JOptionPane.showInputDialog(null, message, type, JOptionPane.INFORMATION_MESSAGE, null,
                            list, DataPanel.this.data);
                    if (obj != null) {
                        DataPanel.this.data = obj;
                        setText(DataPanel.this.data.toString());
                    }
                }
            });
        }
    }

    private class PositionPanel extends JLabel {
        private int line = 0;
        private int colomn = 0;

        private PositionPanel() {
            super(HitakiEditor.getUIString("position.line") + " 0  " + HitakiEditor.getUIString("position.colomn")
                    + " 0");
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int line = Integer.parseInt(JOptionPane.showInputDialog(null,
                            HitakiEditor.getUIString("position.type_a_line_number"),
                            HitakiEditor.getUIString("position.go_to_a_line"), JOptionPane.INFORMATION_MESSAGE));
                    editorPane.setLine(line);
                    PositionPanel.this.line = line;
                    colomn = 0;
                    updateData();
                }
            });
        }

        private void updateData() {
            setText(HitakiEditor.getUIString("position.line") + " " + line + "  "
                    + HitakiEditor.getUIString("position.colomn") + " " + colomn);
        }
    }
}