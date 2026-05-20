package org.example.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoggingExecutorService {
    private final ExecutorService loggingExecutor = Executors.newFixedThreadPool(2);

    public ExecutorService getLoggingExecutor() {
        return loggingExecutor;
    }
}
