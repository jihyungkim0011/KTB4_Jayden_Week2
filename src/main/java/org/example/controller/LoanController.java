package org.example.controller;

import org.example.data.product.Loan;
import org.example.data.product.Saving;
import org.example.executor.LoggingExecutorService;
import org.example.executor.LoggingRunnableLoan;
import org.example.repository.LoanRepository;
import org.example.utils.InputManager;
import org.example.utils.InterestCalculator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;

public class LoanController extends Controller{
    private final LoanRepository loanRepository = new LoanRepository();
    private final ExecutorService executor = LoggingExecutorService.getLoggingExecutor();

    private final Map<Integer, String> menuMap = new TreeMap<>(Map.of(
            1, "대출받기",
            2, "조회"
    ));
    private final Map<Integer, Runnable> methodMap = Map.of(
            1, this::join,
            2, this::getLoanSpec
    );

    @Override
    public void run() {
        System.out.println("==========================");
        System.out.println("[2] 대출을 선택하셨습니다.");
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
        System.out.println("[1] 대출받기를 선택하셨습니다.");

        String productName = InputManager.inputString("계좌명을 입력해주세요: ");
        String userName = InputManager.inputString("사용자명을 입력해주세요: ");
        BigDecimal principal = InputManager.inputMoney("대출받을 금액을 입력해주세요: ");
        int duration = InputManager.inputInt("원하시는 만기 개월을 입력해주세요: ");
        LocalDateTime createdAt = LocalDateTime.now();

        Loan savedLoanAccount = saveLoanProduct(userName, productName, createdAt, duration, principal);

        executeLogging(savedLoanAccount, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName());
        resultView(savedLoanAccount, "대출 신청이 완료되었습니다.");
    }

    private void getLoanSpec() {
        System.out.println("==========================");
        System.out.println("[2] 조회를 선택하셨습니다.");
        System.out.println();
        System.out.println();

        List<Loan> loans = loanRepository.findAll();
        readProductList(loans);

        Long loanId = InputManager.inputLong(
                loans.stream()
                        .map(Loan::getLoanId)
                        .toList(),
                "대출 목록을 선택하세요. 번호선택: "
        );

        Loan findLoanId = loanRepository.findById(loanId);

        executeLogging(findLoanId, this.getClass().getName(),  Thread.currentThread().getStackTrace()[1].getMethodName());
        resultView(findLoanId, "선택하신 대출의 정보입니다.");
    }

    private void executeLogging(Loan savedLoanAccount, String className, String methodName) {
        executor.execute(new LoggingRunnableLoan(savedLoanAccount, className, methodName));
    }

    private void resultView(Loan savedLoanAccount, String message) {
        System.out.println(message);
        System.out.println("계좌명: " + savedLoanAccount.getProductName());
        System.out.println("사용자명: " + savedLoanAccount.getUserName());
        System.out.println("대출액: " + savedLoanAccount.getPrincipal());

        System.out.println("가입일: " + savedLoanAccount.getCreatedAt().truncatedTo(ChronoUnit.DAYS));
        System.out.println("만기일: " + savedLoanAccount.getCreatedAt().plusMonths(savedLoanAccount.getDuration()).truncatedTo(ChronoUnit.DAYS));

        BigDecimal interest = InterestCalculator.calculateLoanInterest(savedLoanAccount.getPrincipal(), Saving.ANNUAL_RATE);
        System.out.println("월이자 금액: " + interest);

        NavigationController.returnHomeList();
    }

    private Loan saveLoanProduct(String userName, String productName, LocalDateTime createdAt, int duration, BigDecimal principal) {
        return loanRepository.save(new Loan(userName, productName, createdAt, duration, principal));
    }

    private void readProductList(List<Loan> loans) {
        if (loans.isEmpty()) {
            System.out.println("받으신 대출이 없습니다. 이전 메뉴로 돌아갑니다.");
            this.run();
        }

        loans.forEach(loan -> System.out.println("[" + loan.getLoanId() + "] " + loan.getProductName() + ", 연이자: " + Loan.ANNUAL_RATE));
        System.out.println();
    }

}
