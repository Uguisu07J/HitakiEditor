package com.github.uguisu32j.hitakieditor.ui;

import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.charset.Charset;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class StatusBar extends JPanel {
    private CodePane editorPane;
    private PositionPanel positionPanel = new PositionPanel();
    private DataPanel<Charset> encodingPanel = new DataPanel<>("エンコーディング", Charset.forName("UTF-8"),
            Charset.availableCharsets().values().toArray(Charset[]::new));
    private DataPanel<EndOfLine> endOfLinePanel = new DataPanel<>("改行コード", EndOfLine.getDefault(), EndOfLine.values());
    private DataPanel<EditorMode> modePanel = new DataPanel<>("言語モード", EditorMode.NONE, EditorMode.values());

    public StatusBar(CodePane editorPane) {
        this.editorPane = editorPane;
        setLayout(new FlowLayout(FlowLayout.RIGHT));
        add(positionPanel);
        add(Box.createGlue());
        add(encodingPanel);
        add(Box.createGlue());
        add(endOfLinePanel);
        add(Box.createGlue());
        add(modePanel);
    }

    public void setPosition(int line, int colomn) {
        positionPanel.line = line;
        positionPanel.colomn = colomn;
        positionPanel.updateData();
    }

    public void setMode(EditorMode mode) {
        modePanel.data = mode;
    }

    public Charset getEncoding() {
        return (Charset) encodingPanel.data;
    }

    public EndOfLine getEndOfLine() {
        return (EndOfLine) endOfLinePanel.data;
    }

    private class DataPanel<T> extends JLabel {
        private Object data;

        private DataPanel(String type, T data, T[] list) {
            super(data.toString());
            this.data = data;
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    Object obj = JOptionPane.showInputDialog(null, type + "を選択してください", type,
                            JOptionPane.INFORMATION_MESSAGE, null, list, DataPanel.this.data);
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
            super("行 0  列 0");
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int line = Integer.parseInt(JOptionPane.showInputDialog(null, "移動する行番号を入力してください", "行の移動",
                            JOptionPane.INFORMATION_MESSAGE));
                    editorPane.setLine(line);
                    PositionPanel.this.line = line;
                    colomn = 0;
                    updateData();
                }
            });
        }

        private void updateData() {
            setText(String.format("行 %d  列 %d", line, colomn));
        }
    }
}