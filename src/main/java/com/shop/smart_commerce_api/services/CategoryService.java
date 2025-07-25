package com.shop.smart_commerce_api.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.shop.smart_commerce_api.dto.request.category.CreateCategoryRequest;
import com.shop.smart_commerce_api.dto.request.filter.CategoryFilterRequest;
import com.shop.smart_commerce_api.dto.response.category.CategoryResponse;
import com.shop.smart_commerce_api.entities.Category;
import com.shop.smart_commerce_api.exception.AppException;
import com.shop.smart_commerce_api.exception.ErrorCode;
import com.shop.smart_commerce_api.mapper.CategoryMapper;
import com.shop.smart_commerce_api.repositories.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<CategoryResponse> getCategories(CategoryFilterRequest filter) {
        Boolean isFetchChildren = filter.getIsFetchChildren() != null ? filter.getIsFetchChildren() : false;
        Boolean isDeleted = filter.getIsDeleted() != null ? filter.getIsDeleted() : false;

        List<Category> categories = new ArrayList<>();
        if (filter.getIsChildren() == null || !filter.getIsChildren()) {
            categories = categoryRepository.findParentCategories(isDeleted);
        } else {
            categories = categoryRepository.findChildrentCategories(isDeleted);
        }
        return categories.stream()
                .map(category -> {
                    List<CategoryResponse> children = new ArrayList<>();
                    if (isFetchChildren) {
                        System.out.println("hello");
                        children = categoryMapper.toCategoryResponses(category.getCategories());
                    }
                    return CategoryResponse.builder()
                            .id(category.getId())
                            .name(category.getName())
                            .children(children)
                            .build();
                })
                .toList();
    }

    public CategoryResponse createCategory(CreateCategoryRequest request) {
        System.out.println(request.getName());
        Category category = categoryRepository.findByNameAndIsDeletedIsFalse(request.getName());
        if (category != null) {
            throw new AppException(ErrorCode.CATEGORY_EXISTS);
        }
        Category savedCategory = categoryRepository.save(categoryMapper.toCategory(request));
        return categoryMapper.toCategoryResponse(savedCategory);
    }

    public void deleteCategory(int categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        category.setIsDeleted(true);
        categoryRepository.save(category);
    }

}
