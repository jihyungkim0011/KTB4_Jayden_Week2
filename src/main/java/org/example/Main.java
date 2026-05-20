package org.example;


import org.example.executor.logging.Logging;

public class Main {
    public static void main(String[] args) {
        Logging.setupLogger();

        new Home().home();
    }
}