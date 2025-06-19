package com.shop.smart_commerce_api.services;

import org.springframework.stereotype.Service;

import com.shop.smart_commerce_api.dto.request.product.ProductVariaRequest;
import com.shop.smart_commerce_api.dto.response.product.ProductResponse;
import com.shop.smart_commerce_api.dto.response.product.ProductVariationResponse;
import com.shop.smart_commerce_api.entities.ProductVariation;
import com.shop.smart_commerce_api.mapper.ProductVariationMapper;
import com.shop.smart_commerce_api.repositories.ProductVariationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductVariationService {

    private final ProductVariationRepository productVariationRepository;
    private final ProductVariationMapper productVariationMapper;

    public ProductVariationResponse create(ProductVariaRequest request) {
        ProductVariation productVariation = productVariationMapper.toProductVariation(request);
        productVariationRepository.save(productVariation);
        return productVariationMapper.toProductVariationResponse(productVariation);
    }

    public ProductVariationResponse findById(int id) {
        ProductVariation productVariation = productVariationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product variation not found"));
        return productVariationMapper.toProductVariationResponse(productVariation);
    }

    public ProductVariationResponse update(int id, ProductVariaRequest request) {
        ProductVariation productVariation = productVariationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product variation not found"));

        productVariationMapper.updateProductVariationFromRequest(request, productVariation);
        productVariationRepository.save(productVariation);
        return productVariationMapper.toProductVariationResponse(productVariation);
    }

    public void delete(int id) {
        ProductVariation productVariation = productVariationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product variation not found"));
        productVariation.setIsDeleted(true);
        productVariationRepository.save(productVariation);
    }
}