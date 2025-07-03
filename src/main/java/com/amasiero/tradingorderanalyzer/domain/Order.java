package com.amasiero.tradingorderanalyzer.domain;

import java.util.Optional;

public record Order(String traderId, int startTime, int endTime) {

    public static Optional<Order> fromCsv(String line) {
        try {
            var parts = line.trim().split(",");
            if (parts.length != 3) {
                return Optional.empty();
            }
            return Optional.of(new Order(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2])));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
