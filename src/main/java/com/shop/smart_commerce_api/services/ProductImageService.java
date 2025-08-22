package com.shop.smart_commerce_api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shop.smart_commerce_api.exception.AppException;
import com.shop.smart_commerce_api.exception.ErrorCode;
import com.shop.smart_commerce_api.entities.ImageProduct;
import com.shop.smart_commerce_api.entities.Product;
import com.shop.smart_commerce_api.repositories.ProductRepository;
import com.shop.smart_commerce_api.repositories.ProductImageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductImageService {
    private final ProductImageRepository productImageRepository;

    public void saveProductImages(List<String> images, int productId) {
        Product product = Product.builder().id(productId).build();
        List<ImageProduct> productImages = images.stream()
                .map(image -> ImageProduct.builder()
                        .imageUrl(image)
                        .product(product)
                        .build())
                .toList();
        productImageRepository.saveAll(productImages);
    }

}
