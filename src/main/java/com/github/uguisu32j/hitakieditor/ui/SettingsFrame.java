package com.github.uguisu32j.hitakieditor.ui;

import java.awt.Color;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class SettingsFrame extends JFrame {
    private String toColorCode(Color color) {
        return String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());
    }
}
