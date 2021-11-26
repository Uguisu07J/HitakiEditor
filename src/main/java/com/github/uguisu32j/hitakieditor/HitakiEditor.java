package com.github.uguisu32j.hitakieditor;

import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.formdev.flatlaf.FlatLightLaf;
import com.github.uguisu32j.hitakieditor.ui.EditorFrame;

public class HitakiEditor {
    public static final String VERSION = "1.0.0";
    private static final String APP_DATA = getAppData();
    public static final FileProperties SETTINGS = new FileProperties(getDefaultSettings(),
            Path.of(APP_DATA, "settings.properties"));

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            if (Files.notExists(Path.of(APP_DATA))) {
                try {
                    Files.createDirectories(Path.of(APP_DATA));
                } catch (IOException e) {
                }
            }
            try {
                SETTINGS.load();
            } catch (IOException e) {
            }
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            try {
                UIManager.setLookAndFeel(new FlatLightLaf());
            } catch (UnsupportedLookAndFeelException e) {
                return;
            }
            new EditorFrame();
        });
    }

    private static String getAppData() {
        String home = System.getProperty("user.home");
        String appData = switch (System.getProperty("os.name")) {
        case "Mac OS X" -> home + "/Library/Application Support/HitakiEditor";
        case "Windows" -> System.getenv("APPDATA") + "/HitakiEditor";
        default -> home + "/.hitakieditor";
        };
        return appData;
    }

    private static Properties getDefaultSettings() {
        Properties defaults = new Properties();
        Rectangle rect = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        defaults.setProperty("frame.editor.x", String.valueOf(rect.x + 100));
        defaults.setProperty("frame.editor.y", String.valueOf(rect.y + 100));
        defaults.setProperty("frame.editor.width", String.valueOf(rect.width / 3 * 2));
        defaults.setProperty("frame.editor.height", String.valueOf(rect.height / 3 * 2));
        return defaults;
    }
}
