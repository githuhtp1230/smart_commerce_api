package com.shop.smart_commerce_api.dto.request.cart;

import java.util.List;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteMultipleCartItemsRequest {
    private List<Integer> cartItems;
}
