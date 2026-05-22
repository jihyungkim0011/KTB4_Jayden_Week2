package org.example.executor.loggingrunnable;

import org.example.data.product.Loan;
import org.example.executor.logging.Logging;

public class LoggingRunnableLoan implements Runnable {
    private final Loan loan;
    private final String className;
    private final String methodName;

    public LoggingRunnableLoan(Loan loan, String className, String methodName) {
        this.loan = loan;
        this.className = className;
        this.methodName = methodName;
    }

    @Override
    public void run() {
        Logging.printLog(
                Thread.currentThread().getName(),
                loan.getUserName(),
                loan.getLoanId(),
                loan.getProductName(),
                className,
                methodName,
                loan.getPrincipal()
        );
    }
}
