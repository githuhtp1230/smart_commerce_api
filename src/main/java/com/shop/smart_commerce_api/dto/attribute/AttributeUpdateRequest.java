package com.shop.smart_commerce_api.dto.attribute;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttributeUpdateRequest {

    private String name;
    private Boolean isDeleted;
}
