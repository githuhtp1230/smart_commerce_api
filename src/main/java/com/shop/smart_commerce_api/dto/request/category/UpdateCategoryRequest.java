package com.shop.smart_commerce_api.dto.request.category;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCategoryRequest {
    private String name;
    private Integer parentId; 
}
