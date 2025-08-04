package com.shop.smart_commerce_api.dto.request.filter;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderFilterRequest {
    private Boolean isPending;
    private Boolean isCompleted;
    private Boolean isCancelled;
}
