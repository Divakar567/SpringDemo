package com.example.library.model;

import lombok.Data;

@Data
public class Cost {
    private Long amount;
    private Currency currency;

    public enum Currency {
        USD, INR
    }
}
