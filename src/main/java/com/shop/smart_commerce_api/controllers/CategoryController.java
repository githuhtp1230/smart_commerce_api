package com.shop.smart_commerce_api.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shop.smart_commerce_api.dto.request.category.CreateCategoryRequest;
import com.shop.smart_commerce_api.dto.request.category.UpdateCategoryRequest;
import com.shop.smart_commerce_api.dto.request.filter.CategoryFilterRequest;
import com.shop.smart_commerce_api.dto.response.ApiResponse;
import com.shop.smart_commerce_api.dto.response.category.CategoryResponse;
import com.shop.smart_commerce_api.services.CategoryService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ApiResponse<List<CategoryResponse>> getCategories(@ModelAttribute CategoryFilterRequest request) {
        return ApiResponse.<List<CategoryResponse>>builder()
                .code(200)
                .message("Get categories successfully")
                .data(categoryService.getCategories(request))
                .build();
    }

    @PostMapping
    public ApiResponse<CategoryResponse> createCategory(@RequestBody CreateCategoryRequest request) {
        return ApiResponse.<CategoryResponse>builder()
                .code(200)
                .message("Create category successfully")
                .data(categoryService.createCategory(request))
                .build();
    }

    @PostMapping("/{categoryId}/delete")
    public ApiResponse<?> deleteCategory(@PathVariable("categoryId") int categoryId) {
        categoryService.deleteCategory(categoryId);
        return ApiResponse.builder()
                .code(200)
                .message("Delete category successfully")
                .build();
    }

    @PutMapping("/{categoryId}/update")
    public ApiResponse<CategoryResponse> updateCategory(
            @PathVariable("categoryId") int categoryId,
            @RequestBody UpdateCategoryRequest request) {
        CategoryResponse updatedCategory = categoryService.updateCategory(categoryId, request);
        return ApiResponse.<CategoryResponse>builder()
                .code(200)
                .message("Update category successfully")
                .data(updatedCategory)
                .build();
    }

    @PostMapping("/{id}")
    ApiResponse<?> toggleIsDeleted(@PathVariable("id") int id) {
        return ApiResponse.builder()
                .code(200)
                .message("Category disabled successfully")
                .data(categoryService.toggleIsDeleted(id))
                .build();
    }
}
