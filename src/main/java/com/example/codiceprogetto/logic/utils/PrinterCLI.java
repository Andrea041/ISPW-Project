package com.example.codiceprogetto.logic.utils;


public class PrinterCLI {
    private PrinterCLI () {}
    public static void printf(String s) {
        printCLI(String.format("%s%n", s));
    }

    public static void print(String s) {
        printCLI(s);
    }

    private static void printCLI(String s) {
        System.out.print(s);
    }
}
