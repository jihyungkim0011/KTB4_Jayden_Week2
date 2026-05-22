package org.example.executor.loggingrunnable;

import org.example.data.product.Saving;
import org.example.executor.logging.Logging;

public class LoggingRunnableSaving implements Runnable {
    private final Saving saving;
    private final String className;
    private final String methodName;

    public LoggingRunnableSaving(Saving saving, String className, String methodName) {
        this.saving = saving;
        this.className = className;
        this.methodName = methodName;
    }

    @Override
    public void run() {
        Logging.printLog(
                Thread.currentThread().getName(),
                saving.getUserName(),
                saving.getSavingId(),
                saving.getProductName(),
                className,
                methodName,
                saving.getPrincipal()
        );
    }
}
