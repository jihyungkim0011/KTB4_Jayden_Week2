package org.example.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InteractionExecutorService {
    private static final ExecutorService interactionExecutor = Executors.newFixedThreadPool(2);

    public static ExecutorService getInteractionExecutor() {
        return interactionExecutor;
    }
}
