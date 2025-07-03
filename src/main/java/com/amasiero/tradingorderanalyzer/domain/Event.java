package com.amasiero.tradingorderanalyzer.domain;

public record Event(int time, EventType type) {
    public int delta() {
        return type.value();
    }
}
