package com.github.uguisu32j.hitakieditor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

@SuppressWarnings("serial")
public class HProperties extends Properties {
    private Path path;
    private boolean defaultsLoaded;

    public HProperties(Path path, Properties defaults, boolean defaultsLoaded) {
        super(defaults);
        this.path = path;
        this.defaultsLoaded = defaultsLoaded;
    }

    public void load() throws IOException {
        if (defaultsLoaded) {
            throw new IllegalStateException();
        }
        load(Files.newBufferedReader(path));
    }

    public void store() throws IOException {
        entrySet().removeIf(e -> e.getValue().equals(defaults.get(e.getKey())));
        store(Files.newBufferedWriter(path), null);
    }
}
