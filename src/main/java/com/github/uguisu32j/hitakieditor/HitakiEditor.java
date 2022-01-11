package com.github.uguisu32j.hitakieditor;

import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
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
    public static final String APP_DATA_DIR = getAppDataDir();
    public static final HProperties SETTINGS = createSettings();
    public static final HProperties WINDOW_SIZE = createWindowSettings();
    private static ResourceBundle LANG;
    private static final Logger LOGGER = LoggerFactory.getLogger(HitakiEditor.class);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
                LOGGER.error("Uncaught Exception", e);
            });
            LOGGER.info("Starting HitakiEditor " + VERSION);
            if (Files.notExists(Path.of(APP_DATA_DIR))) {
                try {
                    Files.createDirectories(Path.of(APP_DATA_DIR));
                } catch (IOException e) {
                    LOGGER.error("Failed to create " + APP_DATA_DIR, e);
                    return;
                }
            }
            try {
                SETTINGS.load();
                WINDOW_SIZE.load();
            } catch (IOException e) {
                LOGGER.warn("Failed to load settings");
            } catch (IllegalStateException e) {
                return;
            }
            try {
                String theme = SETTINGS.getProperty("lookandfeel.theme");
                LookAndFeel laf = switch (theme) {
                    case "Light" -> new FlatLightLaf();
                    case "Dark" -> new FlatDarkLaf();
                    case "IntelliJ" -> new FlatIntelliJLaf();
                    case "Darcula" -> new FlatDarculaLaf();
                    default -> IntelliJTheme.createLaf(
                            new BufferedInputStream(Files.newInputStream(Path.of(APP_DATA_DIR, "themes", theme))));
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
            new EditorFrame();
        });
    }

    private static String getAppDataDir() {
        String home = System.getProperty("user.home");
        String appDataDir = switch (System.getProperty("os.name")) {
            case "Mac OS X" -> home + "/Library/Application Support/HitakiEditor";
            case "Windows" -> System.getenv("APPDATA") + "\\HitakiEditor";
            default -> home + "/.hitakieditor";
        };
        return appDataDir;
    }

    private static HProperties createSettings() {
        var defaults = new Properties();
        var isDefaultsLoaded = false;
        try {
            defaults.load(new BufferedReader(
                    new InputStreamReader(ClassLoader.getSystemResourceAsStream("default_settings.properties"))));
            isDefaultsLoaded = true;
        } catch (IOException e) {
            LOGGER.error("Failed to load default settings", e);
        }
        return new HProperties(
                Path.of(APP_DATA_DIR, "settings.properties"), defaults, isDefaultsLoaded);
    }

    private static HProperties createWindowSettings() {
        var defaults = new Properties();
        Rectangle rect = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        defaults.setProperty("editor.x", String.valueOf(rect.x));
        defaults.setProperty("editor.y", String.valueOf(rect.y));
        defaults.setProperty("editor.width", String.valueOf(rect.width));
        defaults.setProperty("editor.height", String.valueOf(rect.height));
        return new HProperties(
                Path.of(APP_DATA_DIR, "window_settings.properties"), defaults, true);
    }

    public static String getUIText(String key) {
        try {
            return LANG.getString(key);
        } catch (MissingResourceException e) {
            LOGGER.warn("\"" + key + "\" not found (" + SETTINGS.getProperty("lang") + ")");
            return key;
        }
    }
}
