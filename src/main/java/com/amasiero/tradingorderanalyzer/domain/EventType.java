package com.amasiero.tradingorderanalyzer.domain;

public enum EventType {
    START(1),
    END(-1);

    private int value;

    EventType(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

}
