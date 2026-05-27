package org.example.repository;

import org.example.data.account.Account;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AccountRepository {

    private static final Map<Long, Account> store = new ConcurrentHashMap<>();
    private static long sequence = 0L;

    public Account save(Account account) {
        account.incrementAccountId(++sequence);
        store.put(account.getAccountId(), account);
        return account;
    }

    public Account findById(Long accountId) {
        return store.get(accountId);
    }

    public List<Account> findAll() {
        return new ArrayList<>(store.values());
    }

    public void addMoney(Long accountId, BigDecimal money) {
        Account account = findById(accountId);
        BigDecimal amount = account.getAmount();
        account.changeAmount(amount.add(money));
        store.put(accountId, account);
    }

    public void withdraw(Long accountId, BigDecimal money) {
        Account account = findById(accountId);
        BigDecimal amount = account.getAmount();
        account.changeAmount(amount.subtract(money));
        store.put(accountId, account);
    }
}
