package com.shop.smart_commerce_api.dto.request.filter;

import lombok.Data;

@Data
public class UserFilterRequest {
    private Boolean isCustomer;
    private Boolean isMemberShip;
}
