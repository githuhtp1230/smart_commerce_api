package com.shop.smart_commerce_api.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.shop.smart_commerce_api.dto.request.category.CreateCategoryRequest;
import com.shop.smart_commerce_api.dto.request.category.UpdateCategoryRequest;
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
    Category category = categoryRepository.findByNameAndIsDeletedIsFalse(request.getName());
    if (category != null) {
        throw new AppException(ErrorCode.CATEGORY_EXISTS);
    }
    Category newCategory = categoryMapper.toCategory(request);
    newCategory.setIsDeleted(false); 

    if (request.getParentId() != null) {
        Category parentCategory = categoryRepository.findById(request.getParentId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        newCategory.setParent(parentCategory);
    }

    Category savedCategory = categoryRepository.save(newCategory);
    return categoryMapper.toCategoryResponse(savedCategory);
}


    public void deleteCategory(int categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        category.setIsDeleted(true);
        categoryRepository.save(category);
    }

    public CategoryResponse updateCategory(int categoryId, UpdateCategoryRequest request) {
    Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

    if (request.getName() != null && !request.getName().equals(category.getName())) {
        Category existingCategory = categoryRepository.findByNameAndIsDeletedIsFalse(request.getName());
        if (existingCategory != null && existingCategory.getId() != categoryId) {
            throw new AppException(ErrorCode.CATEGORY_EXISTS);
        }
        category.setName(request.getName());
    }

    if (request.getParentId() != null) {
        if (!request.getParentId().equals(category.getParent() != null ? category.getParent().getId() : null)) {
            Category parent = categoryRepository.findById(request.getParentId())
                    .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
            category.setParent(parent);
        }
    }

    Category updated = categoryRepository.save(category);
    return categoryMapper.toCategoryResponse(updated);
}


}
