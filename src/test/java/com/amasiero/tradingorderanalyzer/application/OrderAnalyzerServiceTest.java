package com.amasiero.tradingorderanalyzer.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;


@DisplayName("OrderAnalyzerService Unit Tests")
public class OrderAnalyzerServiceTest {

    private final OrderAnalyzerService service = new OrderAnalyzerService();

    @Nested
    @DisplayName("Valid input scenarios")
    class ValidInputTests {

        @Test
        @DisplayName("Should compute correct max concurrency with overlapping orders")
        void testMaxConcurrencyWithOverlap() {
            List<String> input = List.of(
                "t1,10,20",
                "t1,18,22",
                "t2,15,25",
                "t4,30,40"
            );
            int result = service.findMaxSimultaneouslyActiveOrders(input);
            assertEquals(3, result);
        }

        @Test
        @DisplayName("Should return 1 when there is no overlap")
        void testNoOverlap() {
            List<String> input = List.of(
                "t1,10,15",
                "t2,15,20",
                "t3,20,25"
            );
            int result = service.findMaxSimultaneouslyActiveOrders(input);
            assertEquals(1, result);
        }

        @Test
        @DisplayName("Should return correct count with exact match intervals")
        void testExactMatchIntervals() {
            List<String> input = List.of(
                "t1,10,20",
                "t2,10,20",
                "t3,10,20"
            );
            int result = service.findMaxSimultaneouslyActiveOrders(input);
            assertEquals(3, result);
        }
    }

    @Nested
    @DisplayName("Invalid or edge-case input scenarios")
    class InvalidInputTests {

        @Test
        @DisplayName("Should skip malformed lines and compute correctly")
        void testWithMalformedLines() {
            List<String> input = List.of(
                "t1,10,20",
                "bad,line",
                "t2,15,25"
            );
            int result = service.findMaxSimultaneouslyActiveOrders(input);
            assertEquals(2, result);
        }

        @Test
        @DisplayName("Should return 0 for empty input")
        void testEmptyInput() {
            List<String> input = List.of();
            int result = service.findMaxSimultaneouslyActiveOrders(input);
            assertEquals(0, result);
        }

        @Test
        @DisplayName("Should ignore entries with non-numeric values")
        void testNonNumericValues() {
            List<String> input = List.of(
                "t1,10,20",
                "t2,abc,25"
            );
            int result = service.findMaxSimultaneouslyActiveOrders(input);
            assertEquals(1, result);
        }
    }
}