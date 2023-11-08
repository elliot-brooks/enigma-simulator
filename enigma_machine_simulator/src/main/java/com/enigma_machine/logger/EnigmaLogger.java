package com.enigma_machine.logger;

public class EnigmaLogger {
    private static String logString = "";

    public static void appendLine(String s) {
        logString = logString + s + "\n";
    }

    public static void clearLog() {
        logString = "";
    }

    public static String getLog() {
        return logString;
    }

}