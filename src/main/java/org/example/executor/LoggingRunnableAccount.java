package org.example.executor;

import org.example.data.account.Account;
import org.example.executor.logging.Logging;

public class LoggingRunnableAccount implements Runnable{
    private final Account account;
    private final String className;
    private final String methodName;

    public LoggingRunnableAccount(Account account, String className, String methodName) {
        this.account = account;
        this.className = className;
        this.methodName = methodName;
    }

    @Override
    public void run() {
        Logging.printLog(
                Thread.currentThread().getName(),
                account.getUserName(),
                account.getAccountId(),
                account.getAccountName(),
                className,
                methodName,
                account.getAmount()
        );
    }
}
