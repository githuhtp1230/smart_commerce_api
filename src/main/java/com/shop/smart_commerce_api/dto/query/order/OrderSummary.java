package com.shop.smart_commerce_api.dto.query.order;

import com.shop.smart_commerce_api.entities.Order;

import lombok.Data;

@Data
public class OrderSummary {
    private final Order order;
    private final Integer total;

    public OrderSummary(Order order, Long total) {
        this.order = order;
        this.total = total != null ? total.intValue() : 0; // hoặc xử lý null khác
    }
}
