package com.github.uguisu32j.hitakieditor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

@SuppressWarnings("serial")
public class HProperties extends Properties {
    private Path path;
    private boolean defaultsInitalized;

    public HProperties(Path path, Properties defaults, boolean defaultsInitalized) {
        super(defaults);
        this.path = path;
        this.defaultsInitalized = defaultsInitalized;
    }

    public void load() throws IOException {
        load(Files.newBufferedReader(path));
    }

    public void store() throws IOException {
        entrySet().removeIf(e -> e.getValue().equals(defaults.get(e.getKey())));
        store(Files.newBufferedWriter(path), null);
    }

    public boolean isDefaultsInitalized() {
        return defaultsInitalized;
    }
}
