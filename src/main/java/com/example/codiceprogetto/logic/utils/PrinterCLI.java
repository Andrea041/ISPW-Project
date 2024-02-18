package com.example.codiceprogetto.logic.utils;

import java.util.logging.Logger;

public class PrinterCLI {
    private static final Logger logger = Logger.getLogger(PrinterCLI.class.getName());

    private PrinterCLI() {}
    public static void printf(String s) {
        printCLI(String.format("%s%n", s));
    }

    public static void print(String s) {
        printCLI(s);
    }

    private static void printCLI(String s) {
        logger.info(s);
    }
}
