package com.github.uguisu32j.hitakieditor.ui;

import com.github.uguisu32j.hitakieditor.HitakiEditor;

public enum LangMode {
    NONE;

    @Override
    public String toString() {
        String mode = switch (this) {
            case NONE -> HitakiEditor.getUIString("statusbar.lang_mode.none");
        };
        return mode;
    }
}
