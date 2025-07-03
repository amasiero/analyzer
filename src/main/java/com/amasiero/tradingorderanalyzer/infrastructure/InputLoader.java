package com.amasiero.tradingorderanalyzer.infrastructure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class InputLoader {

    public static List<String> loadFromFile(Path file) throws IOException {
        if (!Files.exists(file)) {
            throw new IOException("🚫 File not found: " + file);
        }
        return Files.readAllLines(file);
    }
}
