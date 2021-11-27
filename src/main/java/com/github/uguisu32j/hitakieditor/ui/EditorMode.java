package com.github.uguisu32j.hitakieditor.ui;

public enum EditorMode {
    NONE;

    @Override
    public String toString() {
        String name = switch (this) {
            case NONE -> "None";
        };
        return name;
    }
}
