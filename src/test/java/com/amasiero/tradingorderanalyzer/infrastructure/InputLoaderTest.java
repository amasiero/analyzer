package com.amasiero.tradingorderanalyzer.infrastructure;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("InputLoader Unit Tests")
class InputLoaderTest {

    private static final Path TEST_FILE = Paths.get("test-orders.csv");
    private static final Path NON_EXISTENT_FILE = Paths.get("non-existent.csv");

    @Nested
    @DisplayName("Valid file scenarios")
    class ValidInputTests {

        @BeforeEach
        void setupFile() throws IOException {
            Files.write(TEST_FILE, List.of(
                "t1,10,20",
                "t2,15,25",
                "t3,30,40"
            ));
        }

        @AfterEach
        void cleanUpFile() throws IOException {
            Files.deleteIfExists(TEST_FILE);
        }

        @Test
        @DisplayName("Should load lines from a valid CSV file")
        void testLoadFromFile() throws IOException {
            List<String> lines = InputLoader.loadFromFile(TEST_FILE);
            assertEquals(3, lines.size());
            assertTrue(lines.get(0).contains("t1"));
        }
    }

    @Nested
    @DisplayName("Invalid file scenarios")
    class InvalidInputTests {

        @Test
        @DisplayName("Should throw IOException if file does not exist")
        void testFileNotFound() {
            IOException exception = assertThrows(IOException.class, () ->
                InputLoader.loadFromFile(NON_EXISTENT_FILE)
            );
            assertTrue(exception.getMessage().contains("File not found"));
        }
    }
}
