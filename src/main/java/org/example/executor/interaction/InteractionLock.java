package org.example.executor.interaction;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class InteractionLock {
    private static final Lock lock = new ReentrantLock();
    private static final Condition condition = lock.newCondition();

    public static Lock getLock() {
        return lock;
    }

    public static Condition getCondition() {
        return condition;
    }


}
