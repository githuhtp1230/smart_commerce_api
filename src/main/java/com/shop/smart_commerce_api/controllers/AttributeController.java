package com.shop.smart_commerce_api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.smart_commerce_api.dto.attribute.AttributeRequest;
import com.shop.smart_commerce_api.dto.attribute.AttributeUpdateRequest;
import com.shop.smart_commerce_api.dto.response.ApiResponse;
import com.shop.smart_commerce_api.dto.response.attribute.AttributeResponse;
import com.shop.smart_commerce_api.services.AttributeService;

import lombok.RequiredArgsConstructor;
import reactor.core.Scannable.Attr;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/attributes")
public class AttributeController {
    @Autowired
    private AttributeService attributeService;

    @PostMapping("/create")
    ApiResponse createAttributeApiResponse(@RequestBody AttributeRequest request) {
        ApiResponse<AttributeResponse> apiResponse = new ApiResponse<>();
        apiResponse.setData(attributeService.create(request));
        return apiResponse;
    }

    @GetMapping("/getAll")
    ApiResponse<List<AttributeResponse>> getALlApiResponse() {
        ApiResponse<List<AttributeResponse>> apiResponse = new ApiResponse<>();
        return apiResponse.<List<AttributeResponse>>builder()
                .code(200)
                .message("Attributes retrieved successfully")
                .data(attributeService.getAll())
                .build();
    }

    @GetMapping("/getById/{id}")
    ApiResponse<AttributeResponse> getid(@PathVariable("id") int id) {
        ApiResponse<AttributeResponse> apiResponse = new ApiResponse<>();
        return apiResponse.<AttributeResponse>builder()
                .code(200)
                .message("Attribute retrieved successfully")
                .data(attributeService.getById(id))
                .build();
    }

    @DeleteMapping("/delete/{id}")
    ApiResponse<AttributeResponse> deletResponse(@PathVariable("id") int id) {
        ApiResponse<AttributeResponse> apiResponse = new ApiResponse<>();
        attributeService.delete(id);
        return apiResponse.<AttributeResponse>builder()
                .code(200)
                .message("Attribute deleted successfully")
                .build();
    }

    @PutMapping("/update/{id}")
    ApiResponse<AttributeResponse> updatResponse(@PathVariable("id") int id,
            @RequestBody AttributeUpdateRequest request) {
        ApiResponse<AttributeResponse> apiResponse = new ApiResponse<>();
        attributeService.update(id, request);
        return apiResponse.<AttributeResponse>builder()
                .code(200)
                .message("Attribute updated successfully")
                .data(attributeService.getById(id))
                .build();
    }
}
