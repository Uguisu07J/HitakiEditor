package com.github.uguisu32j.hitakieditor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

@SuppressWarnings("serial")
public class HProperties extends Properties {
    private Path path;

    public HProperties(Properties defaults, Path path) {
        super(defaults);
        this.path = path;
    }

    public void load() throws IOException {
        if (defaults == null) {
            throw new IllegalStateException();
        }
        load(Files.newBufferedReader(path));
    }

    public void store() throws IOException {
        entrySet().removeIf(e -> e.getValue().equals(defaults.get(e.getKey())));
        store(Files.newBufferedWriter(path), null);
    }
}
