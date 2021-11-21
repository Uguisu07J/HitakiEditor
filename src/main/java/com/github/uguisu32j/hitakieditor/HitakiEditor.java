package com.github.uguisu32j.hitakieditor;

import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.formdev.flatlaf.FlatLightLaf;
import com.github.uguisu32j.hitakieditor.ui.EditorFrame;

public class HitakiEditor {
	public static final String VERSION = "1.0.0";
	public static final Properties SETTINGS = new Properties();

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			System.setProperty("apple.laf.useScreenMenuBar", "true");
			try {
				UIManager.setLookAndFeel(new FlatLightLaf());
			} catch (UnsupportedLookAndFeelException e) {
				return;
			}
			try (BufferedInputStream in = new BufferedInputStream(new FileInputStream("settings.properties"))) {
				SETTINGS.load(in);
			} catch (IOException e) {
			}
			initSettings();
			new EditorFrame();
		});
	}

	private static void initSettings() {
		Rectangle rect = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		SETTINGS.putIfAbsent("frame.editor.x", String.valueOf(rect.x));
		SETTINGS.putIfAbsent("frame.editor.y", String.valueOf(rect.y));
		SETTINGS.putIfAbsent("frame.editor.width", String.valueOf(rect.width));
		SETTINGS.putIfAbsent("frame.editor.height", String.valueOf(rect.height));
		SETTINGS.putIfAbsent("frame.config.x", String.valueOf(rect.x / 2 - 200));
		SETTINGS.putIfAbsent("frame.config.y", String.valueOf(rect.y / 2 - 200));
		SETTINGS.putIfAbsent("frame.config.width", "400");
		SETTINGS.putIfAbsent("frame.config.height", "400");
		SETTINGS.putIfAbsent("frame.search.x", String.valueOf(rect.x / 2 - 200));
		SETTINGS.putIfAbsent("frame.search.y", String.valueOf(rect.y / 2 - 200));
		SETTINGS.putIfAbsent("frame.search.width", "400");
		SETTINGS.putIfAbsent("frame.search.height", "400");
	}
}
