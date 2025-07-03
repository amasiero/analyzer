package com.amasiero.tradingorderanalyzer.application;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.amasiero.tradingorderanalyzer.domain.Event;
import com.amasiero.tradingorderanalyzer.domain.EventType;
import com.amasiero.tradingorderanalyzer.domain.Order;

public class OrderAnalyzerService {

    public int findMaxSimultaneouslyActiveOrders(List<String> rawLines) {
        var events = rawLines.stream()
                .map(Order::fromCsv)
                .flatMap(Optional::stream)
                .flatMap(order -> Stream.of(
                        new Event(order.startTime(), EventType.START),
                        new Event(order.endTime(), EventType.END)))
                .sorted(Comparator
                        .comparingInt(Event::time)
                        .thenComparingInt(e -> e.type().value()))
                .toList();
        
        var active = 0;
        var maxActive = 0;

        for(var e : events) {
            active += e.delta();
            maxActive = Math.max(maxActive, active);
        }

        return maxActive;
    }

}
