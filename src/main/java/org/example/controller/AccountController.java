package org.example.controller;

import org.example.data.account.Account;
import org.example.executor.LoggingExecutorService;
import org.example.executor.logging.Logging;
import org.example.repository.AccountRepository;
import org.example.utils.InputManager;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;

public class AccountController extends Controller {
    private final AccountRepository accountRepository = new AccountRepository();
    ExecutorService executor = new LoggingExecutorService().getLoggingExecutor();

    private final Map<Integer, String> menuMap = new TreeMap<>(Map.of(
            1, "계좌개설",
            2, "입금",
            3, "출금",
            4, "이체"
    ));
    private final Map<Integer, Runnable> methodMap = Map.of(
            1, this::join,
            2, this::addMoney,
            3, this::withdraw,
            4, this::transfer
    );

    @Override
    public void run() {
        System.out.println("==========================");
        System.out.println("[1] 내 계좌/입출금을 선택하셨습니다.");
        System.out.println("도움받고자 하는 메뉴를 선택해주세요.");
        System.out.println();
        System.out.println();
        menuMap.forEach((key, value) -> System.out.println("[" + key + "] " + value));
        System.out.println();
        System.out.println();

        int number = InputManager.inputInt("번호 선택: ");
        System.out.println(number + "번을 선택하셨습니다.");

        getService(number);
    }

    private void getService(int number) {
        methodMap.getOrDefault(number, () -> {
            System.out.println("잘못된 입력입니다.");
            run();
        }).run();
    }

    private void join() {
        System.out.println("==========================");
        System.out.println("[1] 계좌개설을 선택하셨습니다.");

        String accountName = InputManager.inputString("계좌명을 입력해주세요: ");
        String userName = InputManager.inputString("사용자명을 입력해주세요: ");
        BigDecimal money = InputManager.inputMoney("초기 입금할 금액을 입력해주세요: ");

        LocalDateTime createdAt = LocalDateTime.now();

        Account savedAccount = saveAccount(userName, accountName, money, createdAt);

        executor.execute(() -> Logging.printLog(
                LocalDateTime.now(),
                Thread.currentThread().getName(),
                savedAccount.getUserName(),
                savedAccount.getAccountId(),
                savedAccount.getAccountName(),
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                savedAccount.getAmount()
        ));
        executor.execute(() -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println("개설이 완료되었습니다.");
            System.out.println("계좌명: " + savedAccount.getAccountName());
            System.out.println("사용자명: " + savedAccount.getUserName());
            System.out.println("잔액: " + savedAccount.getAmount());
            System.out.println("개설일: " + savedAccount.getCreatedAt().truncatedTo(ChronoUnit.DAYS));
            System.out.println();

            NavigationController.returnHomeList();
        });
    }


    private void addMoney() {
        System.out.println("==========================");
        System.out.println("[2] 입금을 선택하셨습니다.");
        System.out.println();
        System.out.println();

        List<Account> accounts = accountRepository.findAll();
        readAccountList(accounts);

        Long accountId = InputManager.inputLong(
                accounts.stream()
                        .map(Account::getAccountId)
                        .toList(),
                "계좌를 선택하세요. 번호선택: "
        );

        BigDecimal money = InputManager.inputMoney("입금할 금액을 입력해주세요: ");

        accountRepository.addMoney(accountId, money);
        Account findAccount = accountRepository.findById(accountId);

        System.out.println("입금이 완료되었습니다.");
        System.out.println();
        System.out.println("계좌 잔액: " + findAccount.getAmount());

        NavigationController.returnHomeList();
    }

    private void withdraw() {
        System.out.println("==========================");
        System.out.println("[3] 출금을 선택하셨습니다.");
        System.out.println();
        System.out.println();

        List<Account> accounts = accountRepository.findAll();
        readAccountList(accounts);

        Long accountId = InputManager.inputLong(
                accounts.stream()
                        .map(Account::getAccountId)
                        .toList(),
                "계좌를 선택하세요. 번호선택: "
        );

        BigDecimal amount = accountRepository.findById(accountId).getAmount();
        BigDecimal money = InputManager.inputMoney(amount, "출금할 금액을 입력해주세요: ");

        accountRepository.withdraw(accountId, money);
        Account findAccount = accountRepository.findById(accountId);

        System.out.println("출금이 완료되었습니다.");
        System.out.println();
        System.out.println("계좌 잔액: " + findAccount.getAmount());

        NavigationController.returnHomeList();
    }

    private void transfer() {
        System.out.println("==========================");
        System.out.println("[4] 이체을 선택하셨습니다.");
        System.out.println();
        System.out.println();

        List<Account> accounts = accountRepository.findAll();
        readAccountList(accounts);

        Long accountId = InputManager.inputLong(
                accounts.stream()
                        .map(Account::getAccountId)
                        .toList(),
                "계좌를 선택하세요. 번호선택: "
        );

        BigDecimal amount = accountRepository.findById(accountId).getAmount();
        String toUser = InputManager.inputString("계좌명을 입력해주세요: ");
        BigDecimal money = InputManager.inputMoney(amount, "이체할 금액을 입력해주세요: ");

        accountRepository.withdraw(accountId, money);
        Account findAccount = accountRepository.findById(accountId);

        System.out.println("이체가 완료되었습니다.");
        System.out.println();
        System.out.println("받은 사람: " + toUser);
        System.out.println("이체액: " + money);
        System.out.println("계좌 잔액: " + findAccount.getAmount());

        NavigationController.returnHomeList();
    }

    private Account saveAccount(String userName, String accountName, BigDecimal amount, LocalDateTime createdAt) {
        return accountRepository.save(new Account(userName, accountName, amount, createdAt));
    }

    private void readAccountList(List<Account> accounts) {
        if (accounts.isEmpty()) {
            System.out.println("계좌가 없습니다. 이전 메뉴로 돌아갑니다.");
            this.run();
        }

        accounts.forEach(account -> System.out.println("[" + account.getAccountId() + "] " + account.getAccountName()));
        System.out.println();
    }
}
