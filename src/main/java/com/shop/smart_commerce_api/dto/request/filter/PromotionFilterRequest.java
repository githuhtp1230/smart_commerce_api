package com.shop.smart_commerce_api.dto.request.filter;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
public class PromotionFilterRequest {
 @Builder.Default
    Boolean isActived = false;
}
