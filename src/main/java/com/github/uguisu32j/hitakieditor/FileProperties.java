package com.github.uguisu32j.hitakieditor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

@SuppressWarnings("serial")
public class FileProperties extends Properties {
    File file;

    public FileProperties(Properties defaults, String path) {
        super(defaults);
        file = new File(path);
    }

    public void load() throws IOException {
        load(new BufferedReader(new FileReader(file)));
    }

    public void store() throws IOException {
        store(new BufferedWriter(new FileWriter(file)), null);
    }

    public void clean() {
        entrySet().removeIf(e -> e.getValue().equals(defaults.get(e.getKey())));
    }
}
