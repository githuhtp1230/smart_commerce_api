package com.shop.smart_commerce_api.model;

public enum OrderStatus {
    Confirmed(0),
    Cancelled(1),
    Shipping(2),
    Shipped(3);

    private final int value;

    OrderStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
