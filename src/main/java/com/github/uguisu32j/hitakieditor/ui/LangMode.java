package com.github.uguisu32j.hitakieditor.ui;

import com.github.uguisu32j.hitakieditor.HitakiEditor;

public enum LangMode {
    PLAIN;

    @Override
    public String toString() {
        String mode = switch (this) {
            case PLAIN -> HitakiEditor.getUIString("lang_mode.plain");
        };
        return mode;
    }
}
