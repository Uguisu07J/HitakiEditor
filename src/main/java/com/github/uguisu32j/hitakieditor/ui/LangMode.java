package com.github.uguisu32j.hitakieditor.ui;

import com.github.uguisu32j.hitakieditor.HitakiEditor;

public enum LangMode {
    PLAIN_TEXT;

    @Override
    public String toString() {
        var mode = switch (this) {
            case PLAIN_TEXT -> HitakiEditor.getUIText("lang_mode.plain_text");
        };
        return mode;
    }
}
