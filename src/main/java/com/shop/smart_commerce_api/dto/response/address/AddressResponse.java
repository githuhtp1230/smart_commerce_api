package com.shop.smart_commerce_api.dto.response.address;

import java.util.Set;

import com.shop.smart_commerce_api.dto.response.user.UserResponse;
import com.shop.smart_commerce_api.entities.User;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressResponse {
    private Integer id;
    private String province;
    private String district;
    private String ward;
    private String streetAddress;
    private Boolean isDefault;
}
