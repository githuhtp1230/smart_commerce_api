package com.shop.smart_commerce_api.model;

public enum OrderStatus {
    PENDING(0),
    COMPLETED(1),
    CANCELLED(2);

    private final int value;

    OrderStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
