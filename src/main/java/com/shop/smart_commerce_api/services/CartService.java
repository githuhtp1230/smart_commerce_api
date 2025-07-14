package com.shop.smart_commerce_api.services;

import com.shop.smart_commerce_api.dto.request.cart.AddCartItemRequest;
import com.shop.smart_commerce_api.dto.request.cart.DeleteMultipleCartItemsRequest;
import com.shop.smart_commerce_api.dto.response.cart.CartItemResponse;
import com.shop.smart_commerce_api.dto.response.cart.UpdateCartItemQuantityResponse;
import com.shop.smart_commerce_api.dto.response.product.ProductVariationResponse;
import com.shop.smart_commerce_api.entities.AttributeValue;
import com.shop.smart_commerce_api.entities.CartDetail;
import com.shop.smart_commerce_api.entities.Product;
import com.shop.smart_commerce_api.entities.ProductVariation;
import com.shop.smart_commerce_api.exception.AppException;
import com.shop.smart_commerce_api.exception.ErrorCode;
import com.shop.smart_commerce_api.mapper.CartMapper;
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
        private final CartMapper cartMapper;

        public List<CartItemResponse> getCart() {
                return cartDetailRepository.getCartItems(userService.getCurrentUser().getId()).stream().map(
                                cartItemResponse -> {
                                        Product product = productRepository
                                                        .findById(cartItemResponse.getProduct().getId()).get();
                                        if (cartItemResponse.getProductVariation() != null) {
                                                ProductVariation productVariation = productVariationRepository
                                                                .findById(cartItemResponse.getProductVariation()
                                                                                .getId())
                                                                .get();

                                                List<AttributeValue> attributeValues = attributeValueRepository
                                                                .findAttributeValuesByProductVariationId(
                                                                                productVariation.getId());

                                                ProductVariationResponse productVariationResponse = productMapper
                                                                .toProductVariationResponse(productVariation);

                                                productVariationResponse.setAttributeValues(attributeService
                                                                .mapToAttributeValueResponses(
                                                                                attributeValues));

                                                cartItemResponse
                                                                .setProductVariation(productVariationResponse);
                                        }
                                        cartItemResponse.setProduct(productMapper.toProductResponse(product));
                                        return cartItemResponse;
                                }).toList();
        }

        public CartItemResponse addItem(AddCartItemRequest request) {
                Integer userId = userService.getCurrentUser().getId();
                Integer productId = request.getProductId();
                Integer productVariationId = request.getProductVariationId();

                CartDetail existingCartDetail = cartDetailRepository
                                .getCartDetailByUserIdAndProductIdOrProductVariationId(userId, productId,
                                                productVariationId);

                CartDetail cartDetail;
                if (existingCartDetail != null) {
                        existingCartDetail.setQuantity(existingCartDetail.getQuantity() + request.getQuantity());
                        cartDetail = existingCartDetail;
                } else {
                        cartDetail = cartMapper.toCartDetail(request);

                        Product product = productRepository.findById(productId)
                                        .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
                        cartDetail.setProduct(product);

                        if (productVariationId != null) {
                                ProductVariation productVariation = productVariationRepository
                                                .findById(productVariationId)
                                                .orElseThrow(() -> new AppException(
                                                                ErrorCode.PRODUCT_VARIATION_NOT_FOUND));
                                cartDetail.setProductVariation(productVariation);
                        }

                        cartDetail.setUser(userService.getCurrentUser());
                }
                cartDetailRepository.save(cartDetail);

                Product product = productRepository.findById(productId).get();
                CartItemResponse cartItemResponse = cartDetailRepository
                                .getItemByUserIdAndProductIdAndProductVariationId(userId, productId,
                                                productVariationId);

                if (productVariationId != null) {
                        ProductVariation productVariation = productVariationRepository.findById(productVariationId)
                                        .get();
                        List<AttributeValue> attributeValues = attributeValueRepository
                                        .findAttributeValuesByProductVariationId(productVariation.getId());

                        ProductVariationResponse productVariationResponse = productMapper
                                        .toProductVariationResponse(productVariation);

                        productVariationResponse.setAttributeValues(attributeService
                                        .mapToAttributeValueResponses(
                                                        attributeValues));

                        cartItemResponse
                                        .setProductVariation(productMapper
                                                        .toProductVariationResponse(
                                                                        productVariation));
                }
                cartItemResponse.setProduct(productMapper.toProductResponse(product));

                return cartItemResponse;
        }

        public void deleteItem(Integer cartItemId) {
                CartDetail cartDetail = cartDetailRepository.findById(cartItemId)
                                .orElseThrow(() -> new AppException(ErrorCode.CART_ITEM_NOT_FOUND));
                cartDetailRepository.delete(cartDetail);
        }

        public void deleteMultipleCartItem(DeleteMultipleCartItemsRequest request) {
                Integer userId = userService.getCurrentUser().getId();
                List<CartDetail> items = cartDetailRepository.findAllByIdIn(request.getCartItems());
                List<CartDetail> validItems = items.stream()
                                .filter(item -> item.getUser().getId().equals(userId))
                                .toList();

                if (validItems.isEmpty()) {
                        throw new AppException(ErrorCode.CART_ITEM_NOT_FOUND);
                }
                cartDetailRepository.deleteAll(validItems);
        }

        public UpdateCartItemQuantityResponse changeQuantity(Integer change, Integer cartItemId) {
                CartDetail cartDetail = cartDetailRepository.findById(cartItemId)
                                .orElseThrow(() -> new AppException(ErrorCode.CART_ITEM_NOT_FOUND));
                cartDetail.setQuantity(cartDetail.getQuantity() + change);
                cartDetailRepository.save(cartDetail);
                return UpdateCartItemQuantityResponse.builder()
                                .quantity(cartDetail.getQuantity())
                                .product(productMapper.toProductResponse(cartDetail.getProduct()))
                                .build();
        }
}
