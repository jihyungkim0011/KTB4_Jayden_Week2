package org.example.executor.interaction;

import org.example.controller.NavigationController;
import org.example.data.account.Account;
import org.example.executor.InteractionExecutorService;
import org.example.executor.LoggingExecutorService;
import org.example.executor.loggingrunnable.LoggingRunnableAccount;
import org.example.repository.AccountRepository;
import org.example.utils.InputManager;

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

    public void run() {
        while (true) {
            try {
                if (InteractionExecutorService.getInteractionExecutor().isTerminated()) {
                    NavigationController.returnHomeList();
                }

                BigDecimal money = InputManager.inputMoney("입금할 금액을 입력해주세요: ");
                accountRepository.addMoney(account.getAccountId(), money);

                executeLogging(account, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName());
                resultView(account, "입금이 완료되었습니다.");
            } catch (RuntimeException e) {
                throw new RuntimeException("userRunnable RuntimeException: ", e);
            }
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
