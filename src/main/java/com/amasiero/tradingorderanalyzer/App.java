package com.amasiero.tradingorderanalyzer;

import com.amasiero.tradingorderanalyzer.cli.TradingOrderAnalyzerCLI;

import picocli.CommandLine;

public class App {
    public static void main(String[] args) {
        int exitCode = new CommandLine(new TradingOrderAnalyzerCLI()).execute(args);
        System.exit(exitCode);
    }
}
