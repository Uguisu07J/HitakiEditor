package com.github.uguisu32j.hitakieditor.ui;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import com.github.uguisu32j.hitakieditor.HitakiEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class EditorFrame extends JFrame {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public EditorFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(Integer.parseInt(HitakiEditor.WINDOW_SETTINGS.getProperty("editor.x")),
                Integer.parseInt(HitakiEditor.WINDOW_SETTINGS.getProperty("editor.y")),
                Integer.parseInt(HitakiEditor.WINDOW_SETTINGS.getProperty("editor.width")),
                Integer.parseInt(HitakiEditor.WINDOW_SETTINGS.getProperty("editor.height")));
        try {
            setIconImage(ImageIO.read(ClassLoader.getSystemResource("frameicon.png")));
        } catch (IOException e) {
            LOGGER.error("Failed to load frame icon", e);
            return;
        }
        setVisible(true);
    }
}
