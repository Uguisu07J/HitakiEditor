package com.github.uguisu32j.hitakieditor.ui;

public enum EditorMode {
    NONE;

    @Override
    public String toString() {
        return switch (this) {
            case NONE -> "None";
        };
    }
}
