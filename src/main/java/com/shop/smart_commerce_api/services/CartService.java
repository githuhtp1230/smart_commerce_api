package com.shop.smart_commerce_api.services;

import com.shop.smart_commerce_api.dto.response.cart.CartItemResponse;
import com.shop.smart_commerce_api.entities.AttributeValue;
import com.shop.smart_commerce_api.entities.Product;
import com.shop.smart_commerce_api.entities.ProductVariation;
import com.shop.smart_commerce_api.mapper.ProductMapper;
import com.shop.smart_commerce_api.mapper.PromotionMapper;
import com.shop.smart_commerce_api.repositories.AttributeValueRepository;
import com.shop.smart_commerce_api.repositories.CartDetailRepository;
import com.shop.smart_commerce_api.repositories.ProductRepository;
import com.shop.smart_commerce_api.repositories.ProductVariationRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartDetailRepository cartDetailRepository;
    private final ProductRepository productRepository;
    private final UserService userService;
    private final AttributeService attributeService;
    private final AttributeValueRepository attributeValueRepository;
    private final ProductMapper productMapper;
    private final ProductVariationRepository productVariationRepository;

    public List<CartItemResponse> getCart() {
        return cartDetailRepository.getCartItems(userService.getCurrentUser().getId()).stream().map(
                cartItemResponse -> {
                    Product product = productRepository.findById(cartItemResponse.getProduct().getId()).get();
                    if (cartItemResponse.getProductVariation() != null) {
                        ProductVariation productVariation = productVariationRepository
                                .findById(cartItemResponse.getProductVariation().getId()).get();

                        List<AttributeValue> attributeValues = attributeValueRepository
                                .findAttributeValuesByProductVariationId(
                                        productVariation.getId());
                        cartItemResponse
                                .setAttributesValues(attributeService.mapToAttributeValueResponses(attributeValues));
                        cartItemResponse
                                .setProductVariation(productMapper.toProductVariationResponse(productVariation));
                    } else {
                        List<AttributeValue> attributeValues = attributeValueRepository
                                .findAttributeValuesByProductId(product.getId());
                        cartItemResponse
                                .setAttributesValues(attributeService.mapToAttributeValueResponses(attributeValues));
                    }
                    cartItemResponse.setProduct(productMapper.toProductResponse(product));
                    return cartItemResponse;
                }).toList();
    }
}
