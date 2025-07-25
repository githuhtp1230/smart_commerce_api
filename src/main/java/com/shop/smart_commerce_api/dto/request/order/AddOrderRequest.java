package com.shop.smart_commerce_api.dto.request.order;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddOrderRequest {
    private Integer voucherId;
    private Integer paymentId;
    private Integer total;
}
