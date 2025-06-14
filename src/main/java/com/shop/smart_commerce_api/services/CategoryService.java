package com.shop.smart_commerce_api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shop.smart_commerce_api.dto.request.category.CreateCategoryRequest;
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

    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findCategoriesByIsDeletedIsFalseAndParentIsNull()
                .stream().map(category -> {
                    List<CategoryResponse> children = category.getCategories().stream()
                            .map(categoryMapper::toCategoryResponse).toList();
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
