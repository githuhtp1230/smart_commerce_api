package com.shop.smart_commerce_api.dto.request.user;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {
    private String name;
    private String email;
    private String password;
    private String phone;
    private String avatar;
}
