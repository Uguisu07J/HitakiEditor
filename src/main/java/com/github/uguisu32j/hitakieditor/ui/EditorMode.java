package com.github.uguisu32j.hitakieditor.ui;

import com.github.uguisu32j.hitakieditor.HitakiEditor;

public enum EditorMode {
    NONE;

    @Override
    public String toString() {
        String mode = switch (this) {
            case NONE -> HitakiEditor.getUIString("statusbar.mode.none");
        };
        return mode;
    }
}
