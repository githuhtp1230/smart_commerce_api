package com.shop.smart_commerce_api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.smart_commerce_api.dto.request.attribute.AttributeRequest;
import com.shop.smart_commerce_api.dto.request.attribute.AttributeUpdateRequest;
import com.shop.smart_commerce_api.dto.response.ApiResponse;
import com.shop.smart_commerce_api.dto.response.attribute.AttributeResponse;
import com.shop.smart_commerce_api.dto.response.attribute.AttributeValueResponse;
import com.shop.smart_commerce_api.services.AttributeService;
import com.shop.smart_commerce_api.services.AttributeValueService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/attributes")
public class AttributeController {
    private final AttributeService attributeService;
    private final AttributeValueService attributeValueService;

    @PostMapping
    ApiResponse<AttributeResponse> createAttributeApiResponse(@RequestBody AttributeRequest request) {
        return ApiResponse.<AttributeResponse>builder()
                .code(200)
                .message("Get categories successfully")
                .data(attributeService.create(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<AttributeResponse>> getALlApiResponse() {
        return ApiResponse.<List<AttributeResponse>>builder()
                .code(200)
                .message("Attributes retrieved successfully")
                .data(attributeService.getAll())
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<AttributeResponse> getid(@PathVariable("id") int id) {
        return ApiResponse.<AttributeResponse>builder()
                .code(200)
                .message("Attribute retrieved successfully")
                .data(attributeService.getById(id))
                .build();
    }

    @DeleteMapping("/{id}/delete")
    ApiResponse<?> deletResponse(@PathVariable("id") int id) {
        attributeService.delete(id);
        return ApiResponse.builder()
                .code(200)
                .message("Attribute deleted successfully")
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<AttributeResponse> updatResponse(@PathVariable("id") int id,
            @RequestBody AttributeUpdateRequest request) {
        return ApiResponse.<AttributeResponse>builder()
                .code(200)
                .message("Attribute updated successfully")
                .data(attributeService.update(id, request))
                .build();
    }

    @GetMapping("/{id}/attribute-values")
    public ApiResponse<List<AttributeValueResponse>> getAttributeValuesByAttributeId(
            @PathVariable("id") Integer attributeId) {
        return ApiResponse.<List<AttributeValueResponse>>builder()
                .code(200)
                .message("Get attribute values by attribute ID successfully")
                .data(attributeValueService.getAttributeValuesByAttributeId(attributeId))
                .build();
    }
}
