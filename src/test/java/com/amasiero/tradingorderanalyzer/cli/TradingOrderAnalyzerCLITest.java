package com.amasiero.tradingorderanalyzer.cli;

import picocli.CommandLine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


@DisplayName("TradingOrderAnalyzerCLI Tests")
class TradingOrderAnalyzerCLITest {

    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void redirectOutput() {
        System.setOut(new PrintStream(out));
    }

    @AfterEach
    void restoreOutput() {
        System.setOut(originalOut);
    }

    @Nested
    @DisplayName("Valid CLI usage")
    class ValidCLITests {

        @Test
        @DisplayName("Should compute max concurrency using --entry")
        void testWithEntries() {
            String[] args = {
                "--entry", "t1,10,20",
                "--entry", "t1,18,22",
                "--entry", "t2,15,25",
                "--entry", "t4,30,40"
            };

            int exitCode = new CommandLine(new TradingOrderAnalyzerCLI()).execute(args);
            String output = out.toString();

            assertEquals(0, exitCode);
            assertTrue(output.contains("Max simultaneously active orders: 3"));
        }
    }

    @Nested
    @DisplayName("Invalid CLI usage")
    class InvalidCLITests {

        @Test
        @DisplayName("Should fail gracefully with no input")
        void testNoArguments() {
            String[] args = {};

            int exitCode = new CommandLine(new TradingOrderAnalyzerCLI()).execute(args);
            String output = out.toString();

            assertEquals(1, exitCode);
            assertTrue(output.contains("trading-order-analyzer"));
            assertTrue(output.contains("Usage:"));
        }

        @Test
        @DisplayName("Should handle malformed input gracefully")
        void testWithMalformedEntry() {
            String[] args = {
                "--entry", "bad,input",
                "--entry", "t2,10,20"
            };

            int exitCode = new CommandLine(new TradingOrderAnalyzerCLI()).execute(args);
            String output = out.toString();

            assertEquals(0, exitCode);
            assertTrue(output.contains("Max simultaneously active orders: 1"));
        }
    }
}
