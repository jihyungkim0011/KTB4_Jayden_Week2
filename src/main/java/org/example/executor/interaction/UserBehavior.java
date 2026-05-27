package org.example.executor.interaction;

import org.example.data.account.Account;
import org.example.executor.LoggingExecutorService;
import org.example.executor.loggingrunnable.LoggingRunnableAccount;
import org.example.repository.AccountRepository;
import org.example.utils.InputManager;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;

public class UserBehavior {

    private final Account account;

    private final AccountRepository accountRepository;
    private final ExecutorService executor = LoggingExecutorService.getLoggingExecutor();

    public UserBehavior(Account account, AccountRepository accountRepository) {
        this.account = account;
        this.accountRepository = accountRepository;
    }

    public void simulateUser() throws IOException, InterruptedException {
        while (!Thread.currentThread().isInterrupted()) {
            System.out.println("입금할 금액을 입력해주세요.");
            while (System.in.available() == 0) {
                Thread.sleep(50);
            }
            BigDecimal money = InputManager.inputMoney("");
            accountRepository.addMoney(account.getAccountId(), money);

            executeLogging(account, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName());
            resultView(account, "입금이 완료되었습니다.");
        }
    }

    private void executeLogging(Account account, String className, String methodName) {
        executor.execute(new LoggingRunnableAccount(account, className, methodName));
    }

    private void resultView(Account account, String message) {
        System.out.println(message);
        System.out.println("잔액: " + account.getAmount());
    }
}
