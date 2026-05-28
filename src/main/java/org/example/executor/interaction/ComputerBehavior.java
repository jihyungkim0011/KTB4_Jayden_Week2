package org.example.executor.interaction;

import org.example.data.account.Account;
import org.example.executor.LoggingExecutorService;
import org.example.executor.loggingrunnable.LoggingRunnableAccount;
import org.example.repository.AccountRepository;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;

public class ComputerBehavior {
    private int count = 0;
    private final Account account;
    private final AccountRepository accountRepository;
    private final ExecutorService executor = LoggingExecutorService.getLoggingExecutor();

    private final BigDecimal subscription = BigDecimal.valueOf(29000);

    public ComputerBehavior(Account account, AccountRepository accountRepository) {
        this.account = account;
        this.accountRepository = accountRepository;
    }

    public void simulateComputer() {
        while (count < 100) {
            try {
                Thread.sleep(5_000);

                if (account.getAmount().compareTo(subscription) < 0) {
                    break;
                }
                accountRepository.withdraw(account.getAccountId(), subscription);

                executeLogging(account, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName());
                resultView(account, "구독료가 결제되었습니다.");

                count++;
            } catch (InterruptedException e) {
                throw new RuntimeException("computerRunnable InterruptedException: ", e);
            }
        }
        System.out.println();
        System.out.println();
        if (count < 100) {
            System.out.println("구독료를 지불하지 못했습니다.");
        } else {
            System.out.println("구독료 100회 지불하셨습니다.");
            System.out.println("거래 시뮬레이션을 성공적으로 마치셨습니다.");
        }
        System.out.println("메뉴로 돌아갑니다.");
        System.out.println();
    }

    private void executeLogging(Account account, String className, String methodName) {
        executor.execute(new LoggingRunnableAccount(account, className, methodName));
    }

    private void resultView(Account account, String message) {
        System.out.println();
        System.out.println(message);
        System.out.println("잔액: " + account.getAmount());
    }
}
