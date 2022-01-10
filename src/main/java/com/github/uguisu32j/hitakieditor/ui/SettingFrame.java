package com.github.uguisu32j.hitakieditor.ui;

import java.awt.Color;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class SettingFrame extends JFrame {
    private static String encodeColor(Color color) {
        return String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());
    }
}
