package com.shop.smart_commerce_api.dto.request.category;

import lombok.Data;
import lombok.Getter;

@Data
public class CreateCategoryRequest {
    private String name;
    private Integer parentId;
}
