package com.github.uguisu32j.hitakieditor;

import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.IntelliJTheme;
import com.github.uguisu32j.hitakieditor.ui.EditorFrame;

public class HitakiEditor {
    public static final String VERSION = "1.0.0";
    private static final String APP_DATA = getAppData();
    public static final FileProperties SETTINGS = new FileProperties(getDefaultSettings(),
            Path.of(APP_DATA, "settings.properties"));
    public static final FileProperties WINDOW_SIZE = new FileProperties(getDefaultWindowSize(),
            Path.of(APP_DATA, "window.properties"));
    private static final Logger LOGGER = LoggerFactory.getLogger(HitakiEditor.class);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("Starting HitakiEditor " + VERSION);
            }
            if (Files.notExists(Path.of(APP_DATA))) {
                try {
                    Files.createDirectories(Path.of(APP_DATA));
                } catch (IOException e) {
                    if (LOGGER.isErrorEnabled()) {
                        LOGGER.error("Failed to create " + APP_DATA, e);
                    }
                    return;
                }
            }
            try {
                SETTINGS.load();
                WINDOW_SIZE.load();
            } catch (IOException e) {
                LOGGER.error("Failed to load settings", e);
            }
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            try {
                String theme = SETTINGS.getProperty("lookandfeel.theme");
                LookAndFeel lookAndFeel = switch (theme) {
                    case "FlatLightLaf" -> new FlatLightLaf();
                    case "FlatDarkLaf" -> new FlatDarkLaf();
                    default -> IntelliJTheme.createLaf(ClassLoader.getSystemResourceAsStream(theme));
                };
                UIManager.setLookAndFeel(lookAndFeel);
                UIManager.put("accentBaseColor", SETTINGS.getProperty("lookandfeel.accentcolor"));
            } catch (UnsupportedLookAndFeelException | IOException e) {
                LOGGER.error("Failed to set theme", e);
                return;
            }
            new EditorFrame(args.length > 0 ? Path.of(args[0]) : null);
        });
    }

    private static String getAppData() {
        String home = System.getProperty("user.home");
        String appData = switch (System.getProperty("os.name")) {
            case "Mac OS X" -> home + "/Library/Application Support/HitakiEditor";
            case "Windows" -> System.getenv("APPDATA") + "\\HitakiEditor";
            default -> home + "/.hitakieditor";
        };
        return appData;
    }

    private static Properties getDefaultSettings() {
        Properties defaults = new Properties();
        defaults.setProperty("lookandfeel.theme", "FlatLightLaf");
        defaults.setProperty("lookandfeel.accentcolor", "#2675BF");
        return defaults;
    }

    private static Properties getDefaultWindowSize() {
        Properties defaults = new Properties();
        Rectangle rect = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        defaults.setProperty("frame.editor.x", String.valueOf(rect.x + 100));
        defaults.setProperty("frame.editor.y", String.valueOf(rect.y + 100));
        defaults.setProperty("frame.editor.width", String.valueOf(rect.width));
        defaults.setProperty("frame.editor.height", String.valueOf(rect.height));
        return defaults;
    }
}
