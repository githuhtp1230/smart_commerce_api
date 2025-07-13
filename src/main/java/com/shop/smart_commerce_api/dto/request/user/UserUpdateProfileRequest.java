package com.shop.smart_commerce_api.dto.request.user;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateProfileRequest {
    private String name;
    private String avatar;
    private String phone;
}
