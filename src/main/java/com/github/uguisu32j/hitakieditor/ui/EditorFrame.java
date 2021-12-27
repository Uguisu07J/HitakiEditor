package com.github.uguisu32j.hitakieditor.ui;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.uguisu32j.hitakieditor.HitakiEditor;

@SuppressWarnings("serial")
public class EditorFrame extends JFrame {
    private static final Logger LOGGER = LoggerFactory.getLogger(EditorFrame.class);

    public EditorFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(Integer.parseInt(HitakiEditor.WINDOW_SIZE.getProperty("editor.x")),
                Integer.parseInt(HitakiEditor.WINDOW_SIZE.getProperty("editor.y")),
                Integer.parseInt(HitakiEditor.WINDOW_SIZE.getProperty("editor.width")),
                Integer.parseInt(HitakiEditor.WINDOW_SIZE.getProperty("editor.height")));
        try {
            setIconImage(ImageIO.read(ClassLoader.getSystemResourceAsStream("frameicon.png")));
        } catch (IOException e) {
            LOGGER.error("Failed to load frame icon", e);
            return;
        }
        setVisible(true);
    }
}
