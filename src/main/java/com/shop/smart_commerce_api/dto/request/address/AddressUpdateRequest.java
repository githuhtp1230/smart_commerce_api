package com.shop.smart_commerce_api.dto.request.address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressUpdateRequest {
    private String province;
    private String district;
    private String ward;
    private String streetAddress;
}
