package com.shop.smart_commerce_api.dto.request.order;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddOrderDetailRequest {
    private Integer productId;
    private Integer productVariationId;
    private Integer orderId;
    private Integer quantity;
    private Double price;
}
