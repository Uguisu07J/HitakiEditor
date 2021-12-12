package com.github.uguisu32j.hitakieditor.ui;

public enum EditorMode {
    NONE;

    @Override
    public String toString() {
        String mode = switch (this) {
            case NONE -> "None";
        };
        return mode;
    }
}
