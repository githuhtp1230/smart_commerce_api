package com.shop.smart_commerce_api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.shop.smart_commerce_api.dto.response.ApiResponse;
import com.shop.smart_commerce_api.services.AttributeValueService;
import com.shop.smart_commerce_api.dto.response.attribute.AttributeValueResponse;
import com.shop.smart_commerce_api.dto.request.attribute.AttributeValueRequest;
import com.shop.smart_commerce_api.dto.request.attribute.AttributeValueUpdateRequest;
import com.shop.smart_commerce_api.dto.request.filter.AttributeValueFilterRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/attribute-values")
@RequiredArgsConstructor
public class AttributeValueController {

    private final AttributeValueService attributeValueService;

    // Lấy danh sách tất cả AttributeValue với filter
    @GetMapping
    public ApiResponse<List<AttributeValueResponse>> getAttributeValues(
            @ModelAttribute AttributeValueFilterRequest request) {
        return ApiResponse.<List<AttributeValueResponse>>builder()
                .code(200)
                .message("Lấy danh sách giá trị thuộc tính thành công")
                .data(attributeValueService.getAttributeValues(request))
                .build();
    }

    // Thêm mới AttributeValue
    @PostMapping
    public ApiResponse<AttributeValueResponse> createAttributeValue(
            @RequestBody AttributeValueRequest request) {
        return ApiResponse.<AttributeValueResponse>builder()
                .code(200)
                .message("Tạo giá trị thuộc tính thành công")
                .data(attributeValueService.createAttributeValue(request))
                .build();
    }

    // Cập nhật AttributeValue
    @PutMapping("/{attributeValueId}")
    public ApiResponse<AttributeValueResponse> updateAttributeValue(
            @PathVariable("attributeValueId") int attributeValueId,
            @RequestBody AttributeValueUpdateRequest request) {
        AttributeValueResponse updated = attributeValueService.updateAttributeValue(attributeValueId, request);
        return ApiResponse.<AttributeValueResponse>builder()
                .code(200)
                .message("Cập nhật giá trị thuộc tính thành công")
                .data(updated)
                .build();
    }

    // Xoá mềm AttributeValue
    @PostMapping("/{attributeValueId}/delete")
    public ApiResponse<Void> deleteAttributeValue(@PathVariable("attributeValueId") int attributeValueId) {
        attributeValueService.deleteAttributeValue(attributeValueId);
        return ApiResponse.<Void>builder()
                .code(200)
                .message("Xoá giá trị thuộc tính thành công")
                .build();
    }

    // Lấy danh sách giá trị theo attributeId (phục vụ combobox)
    @GetMapping("/by-attribute/{attributeId}")
    public ApiResponse<List<AttributeValueResponse>> getAttributeValuesByAttributeId(
            @PathVariable("attributeId") Integer attributeId) {
        return ApiResponse.<List<AttributeValueResponse>>builder()
                .code(200)
                .message("Lấy giá trị theo thuộc tính thành công")
                .data(attributeValueService.getAttributeValuesByAttributeId(attributeId))
                .build();
    }
}
