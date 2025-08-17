package com.shop.smart_commerce_api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.shop.smart_commerce_api.dto.response.ApiResponse;
import com.shop.smart_commerce_api.services.AttributeValueService;
import com.shop.smart_commerce_api.dto.response.attribute.AttributeValueResponse;
import com.shop.smart_commerce_api.dto.request.attribute.AttributeValueRequest;
import com.shop.smart_commerce_api.dto.request.attribute.AttributeValueUpdateRequest;
import com.shop.smart_commerce_api.dto.request.filter.AttributeValueFilterRequest;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/attribute-values")
@AllArgsConstructor
public class AttributeValueController {
    private final AttributeValueService attributeValueService;

    @GetMapping
    public ApiResponse<List<AttributeValueResponse>> getAttributeValues(
            @ModelAttribute AttributeValueFilterRequest request,
            @RequestParam(required = false) Boolean isDeleted) {

        List<AttributeValueResponse> attributeValues;

        if (isDeleted != null) {
            attributeValues = attributeValueService.getAllAttributeValueByIsDeleted(isDeleted);
        } else {
            attributeValues = attributeValueService.getAttributeValues(request);
        }

        return ApiResponse.<List<AttributeValueResponse>>builder()
                .code(200)
                .message("Get attribute values successfully")
                .data(attributeValues)
                .build();
    }

    @PostMapping
    public ApiResponse<AttributeValueResponse> createAttributeValue(@RequestBody AttributeValueRequest request) {
        return ApiResponse.<AttributeValueResponse>builder()
                .code(200)
                .message("Create attribute value successfully")
                .data(attributeValueService.createAttributeValue(request))
                .build();
    }

    @PostMapping("/{id}")
    ApiResponse<?> toggleIsDeleted(@PathVariable("id") int id) {
        return ApiResponse.builder().code(200).message("Attribute value disable successfully")
                .data(attributeValueService.toggleIsDeleted(id)).build();
    }

    @DeleteMapping("/{attributeValueId}/delete")
    public ApiResponse<?> deleteAttributeValue(@PathVariable("attributeValueId") int attributeValueId) {
        attributeValueService.deleteAttributeValue(attributeValueId);
        return ApiResponse.builder()
                .code(200)
                .message("Delete attribute value successfully")
                .build();
    }

    @PutMapping("/{attributeValueId}/update")
    public ApiResponse<?> updateAttributeValue(@PathVariable("attributeValueId") int attributeValueId,
            @RequestBody AttributeValueUpdateRequest request) {
        attributeValueService.updateAtrributeValue(attributeValueId, request);
        return ApiResponse.builder()
                .code(200)
                .message("Update attribute value successfully")
                .data(attributeValueService.updateAtrributeValue(attributeValueId, request))
                .build();
    }

}
