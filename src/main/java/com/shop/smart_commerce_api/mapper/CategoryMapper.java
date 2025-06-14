package com.shop.smart_commerce_api.mapper;

import org.mapstruct.Mapper;

import com.shop.smart_commerce_api.dto.request.category.CreateCategoryRequest;
import com.shop.smart_commerce_api.dto.response.category.CategoryResponse;
import com.shop.smart_commerce_api.entities.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponse toCategoryResponse(Category category);

    Category toCategory(CreateCategoryRequest request);
}
