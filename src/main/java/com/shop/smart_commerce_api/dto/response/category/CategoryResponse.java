package com.shop.smart_commerce_api.dto.response.category;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryResponse {
    private Integer id;
    private String name;
    private List<CategoryResponse> children;
}
