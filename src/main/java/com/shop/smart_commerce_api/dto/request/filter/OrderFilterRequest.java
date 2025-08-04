package com.shop.smart_commerce_api.dto.request.filter;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderFilterRequest {
    private Boolean Confirmed;
    private Boolean Cancelled;
    private Boolean Shipping;
    private Boolean Shipped;
}
