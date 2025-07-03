package com.amasiero.tradingorderanalyzer.cli;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import com.amasiero.tradingorderanalyzer.application.OrderAnalyzerService;
import com.amasiero.tradingorderanalyzer.infrastructure.InputLoader;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "trading-order-analyzer", mixinStandardHelpOptions = true, version = "1.0", description = "Calculates the maximum number of simultaneously active trading orders.")
public class TradingOrderAnalyzerCLI implements Callable<Integer> {

    @Option(names = { "-f", "--file" }, description = "Path to CSV file with trading orders.")
    private Path csvPath;

    @Option(names = { "-e", "--entry" }, description = "Individual order entries like t1,10,20")
    private List<String> entries = new ArrayList<>();

    private final OrderAnalyzerService service = new OrderAnalyzerService();

    @Override
    public Integer call() throws Exception {
        try {

            var lines = new ArrayList<String>();

            if (csvPath == null && entries.isEmpty()) {
                System.err.println("❌ Please provide either --file or at least one --entry.");
                CommandLine.usage(this, System.out);
                return 1;
            }
            
            if (csvPath != null) {
                lines.addAll(InputLoader.loadFromFile(csvPath));
            }

            if (!entries.isEmpty()) {
                lines.addAll(entries);
            }

            if (lines.isEmpty()) {
                System.err.println("❌ Please provide either --file or at least one --entry");
                return 1;
            }

            int result = service.findMaxSimultaneouslyActiveOrders(lines);
            System.out.println("✅ Max simultaneously active orders: " + result + " 🧵");
            return 0;
        } catch (IOException e) {
            System.err.println("❌ Error: " + e.getMessage());
            return 1;
        }
    }

}
