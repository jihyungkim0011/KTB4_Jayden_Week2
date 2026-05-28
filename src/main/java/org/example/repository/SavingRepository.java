package org.example.repository;

import org.example.data.product.Saving;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SavingRepository {

    private final Map<Long, Saving> store = new HashMap<>();
    private long sequence = 0L;

    public Saving save(Saving saving) {
        saving.incrementSavingId(++sequence);
        store.put(saving.getSavingId(), saving);
        return saving;
    }

    public Saving findById(Long savingId) {
        return store.get(savingId);
    }

    public List<Saving> findAll() {
        return new ArrayList<>(store.values());
    }
}
