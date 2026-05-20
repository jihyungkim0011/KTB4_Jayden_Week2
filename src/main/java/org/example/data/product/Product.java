package org.example.data.product;

import org.example.data.FinancialContract;

import java.time.LocalDateTime;

public class Product extends FinancialContract {
    private String productName;
    private LocalDateTime createdAt;
    private int duration; // 개월

    public Product(String userName, String productName, LocalDateTime createdAt, int duration) {
        super(userName);
        this.productName = productName;
        this.createdAt = createdAt;
        this.duration = duration;
    }

    public String getProductName() {
        return productName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public int getDuration() {
        return duration;
    }
}
