package com.github.uguisu32j.hitakieditor.ui;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class EditorFrame extends JFrame {
	public EditorFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// setBounds(Integer.parseInt(HitakiEditor.SETTINGS.getProperty("frame.editor.x")),
		// Integer.parseInt(HitakiEditor.SETTINGS.getProperty("frame.editor.y")),
		// Integer.parseInt(HitakiEditor.SETTINGS.getProperty("frame.editor.width")),
		// Integer.parseInt(HitakiEditor.SETTINGS.getProperty("frame.editor.height")));
		setVisible(true);
	}
}