package com.github.uguisu32j.hitakieditor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

@SuppressWarnings("serial")
public class FileProperties extends Properties {
    private Path path;

    public FileProperties(Properties defaults, Path path) {
        super(defaults);
        this.path = path;
    }

    public void load() throws IOException {
        load(Files.newBufferedReader(path));
    }

    public void store() throws IOException {
        entrySet().removeIf(e -> e.getValue().equals(defaults.get(e.getKey())));
        store(Files.newBufferedWriter(path), null);
    }
}
