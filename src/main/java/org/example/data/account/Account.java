package org.example.data.account;

import org.example.data.FinancialContract;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Account extends FinancialContract {
    private Long accountId;
    private String accountName;
    private BigDecimal amount; // 계좌 잔액
    private LocalDateTime createdAt;

    public Account(String userName, String accountName, BigDecimal amount, LocalDateTime createdAt) {
        super(userName);
        this.accountName = accountName;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public Long getAccountId() {
        return accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void incrementAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public void deposit(BigDecimal amount, BigDecimal money) {
        this.amount = amount.add(money);
    }

    public void withdraw(BigDecimal amount,  BigDecimal money) {
        this.amount = amount.subtract(money);
    }
}
