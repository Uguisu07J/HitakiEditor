package com.github.uguisu32j.hitakieditor;

import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.IntelliJTheme;
import com.github.uguisu32j.hitakieditor.ui.EditorFrame;

public class HitakiEditor {
    public static final String VERSION = "1.0.0";
    private static final String APP_DATA = getAppData();
    public static final HProperties SETTINGS = new HProperties(getDefaultSettings(),
            Path.of(APP_DATA, "settings.properties"));
    public static final HProperties WINDOW_SIZE = new HProperties(getDefaultWindowSize(),
            Path.of(APP_DATA, "window_size.properties"));
    private static ResourceBundle LANG;
    private static final Logger LOGGER = LoggerFactory.getLogger(HitakiEditor.class);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Thread.setDefaultUncaughtExceptionHandler((Thread t, Throwable e) -> {
                LOGGER.error("Uncaught Exception", e);
            });
            LOGGER.info("Starting HitakiEditor " + VERSION);
            if (Files.notExists(Path.of(APP_DATA))) {
                try {
                    Files.createDirectories(Path.of(APP_DATA));
                } catch (IOException e) {
                    LOGGER.error("Failed to create " + APP_DATA, e);
                    return;
                }
            }
            try {
                SETTINGS.load();
                WINDOW_SIZE.load();
            } catch (IOException e) {
                LOGGER.warn("Failed to load settings", e);
            } catch (IllegalStateException e) {
                return;
            }
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            try {
                String theme = SETTINGS.getProperty("lookandfeel.theme");
                LookAndFeel laf = switch (theme) {
                    case "Light" -> new FlatLightLaf();
                    case "Dark" -> new FlatDarkLaf();
                    case "IntelliJ" -> new FlatIntelliJLaf();
                    case "Darcula" -> new FlatDarculaLaf();
                    default -> IntelliJTheme.createLaf(
                            new BufferedInputStream(Files.newInputStream(Path.of(APP_DATA, "themes", theme))));
                };
                UIManager.setLookAndFeel(laf);
            } catch (UnsupportedLookAndFeelException | IOException e) {
                LOGGER.error("Failed to load theme", e);
                return;
            }
            UIManager.put("accentBaseColor", SETTINGS.getProperty("lookandfeel.accent_color"));
            try {
                LANG = ResourceBundle.getBundle("lang/lang", new Locale(SETTINGS.getProperty("lang")));
            } catch (MissingResourceException e) {
                LOGGER.error("Failed to load language file", e);
                return;
            }
            new EditorFrame(Arrays.stream(args).map(s -> Path.of(s)).toArray(Path[]::new));
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
        try {
            defaults.load(new BufferedReader(
                    new InputStreamReader(ClassLoader.getSystemResourceAsStream("default_settings.properties"))));
        } catch (IOException e) {
            LOGGER.error("Failed to load default settings", e);
            return null;
        }
        return defaults;
    }

    private static Properties getDefaultWindowSize() {
        Properties defaults = new Properties();
        Rectangle rect = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        defaults.setProperty("editor.x", String.valueOf(rect.x + 100));
        defaults.setProperty("editor.y", String.valueOf(rect.y + 100));
        defaults.setProperty("editor.width", String.valueOf(rect.width));
        defaults.setProperty("editor.height", String.valueOf(rect.height));
        return defaults;
    }

    public static String getUIString(String key) {
        try {
            return LANG.getString(key);
        } catch (MissingResourceException e) {
            LOGGER.warn("\"" + key + "\" not found (" + SETTINGS.getProperty("lang") + ")");
            return key;
        }
    }
}
