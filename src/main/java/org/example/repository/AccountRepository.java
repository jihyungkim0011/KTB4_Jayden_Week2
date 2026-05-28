package org.example.repository;

import org.example.data.account.Account;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AccountRepository {

    private static final Map<Long, Account> store = new ConcurrentHashMap<>();
    private static long sequence = 0L;

    private final Lock lock = new ReentrantLock();

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
        lock.lock();
        try {
            Account account = findById(accountId);
            BigDecimal amount = account.getAmount();
            account.deposit(amount, money);
            store.put(accountId, account);
        } finally {
            lock.unlock();
        }
    }

    public void withdraw(Long accountId, BigDecimal money) {
        lock.lock();
        try {
            Account account = findById(accountId);
            BigDecimal amount = account.getAmount();
            account.withdraw(amount, money);
            store.put(accountId, account);
        } finally {
            lock.unlock();
        }
    }
}
