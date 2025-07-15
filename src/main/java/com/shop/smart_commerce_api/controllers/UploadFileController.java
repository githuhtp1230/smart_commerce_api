package com.shop.smart_commerce_api.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shop.smart_commerce_api.dto.response.ApiResponse;
import com.shop.smart_commerce_api.services.CloudinaryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/upload")
@RequiredArgsConstructor
public class UploadFileController {
    private final CloudinaryService cloudinaryService;

    @PostMapping("/file")
    public ApiResponse<String> uploadFile(@PathVariable("file") MultipartFile file) {
        return ApiResponse.<String>builder()
                .data(cloudinaryService.uploadFile(file))
                .message("File uploaded successfully")
                .build();
    }
}
