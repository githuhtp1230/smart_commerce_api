package com.shop.smart_commerce_api.services;

import java.util.List;

import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import com.shop.smart_commerce_api.dto.request.order.AddOrderDetailRequest;
import com.shop.smart_commerce_api.dto.response.order.OrderDetailResponse;
import com.shop.smart_commerce_api.dto.response.order.OrderResponse;
import com.shop.smart_commerce_api.dto.response.product.ProductResponse;
import com.shop.smart_commerce_api.dto.response.product.ProductVariationResponse;
import com.shop.smart_commerce_api.entities.Order;
import com.shop.smart_commerce_api.entities.OrderDetail;
import com.shop.smart_commerce_api.entities.Product;
import com.shop.smart_commerce_api.entities.ProductVariation;
import com.shop.smart_commerce_api.entities.User;
import com.shop.smart_commerce_api.exception.AppException;
import com.shop.smart_commerce_api.exception.ErrorCode;
import com.shop.smart_commerce_api.mapper.OrderDetailMapper;
import com.shop.smart_commerce_api.mapper.OrderMapper;
import com.shop.smart_commerce_api.mapper.ProductMapper;
import com.shop.smart_commerce_api.repositories.OrderDetailRepository;
import com.shop.smart_commerce_api.repositories.OrderRepository;
import com.shop.smart_commerce_api.repositories.ProductRepository;
import com.shop.smart_commerce_api.repositories.ProductVariationRepository;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderDetailService {
        private final OrderDetailRepository orderDetailRepository;
        private final OrderDetailMapper orderDetailMapper;
        private final OrderService orderService;
        private final OrderMapper orderMapper;
        private final OrderRepository orderRepository;
        private final UserService userService;
        private final ProductVariationRepository productVariationRepository;
        private final ProductVariationService productVariationService;
        private final ProductRepository productRepository;
        private final ProductMapper productMapper;

        public List<OrderDetailResponse> getOrderDetails(Integer orderId) {
                Order order = orderRepository.findById(orderId)
                                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
                return orderDetailRepository.getOrderDetails(orderId).stream().map(
                                orderDetailResponse -> {
                                        Product product = productRepository
                                                        .findById(orderDetailResponse.getProduct().getId()).get();
                                        if (orderDetailResponse.getProductVariation() != null) {
                                                ProductVariationResponse productVariationResponse = productVariationService
                                                                .mapToVariationResponse(
                                                                                orderDetailResponse
                                                                                                .getProductVariation()
                                                                                                .getId());
                                                orderDetailResponse
                                                                .setProductVariation(productVariationResponse);
                                        }
                                        orderDetailResponse.setProduct(productMapper.toProductResponse(product));
                                        return orderDetailResponse;
                                }).toList();
        }

        public OrderDetailResponse addOrderDetail(AddOrderDetailRequest request) {
                // Lấy thông tin người dùng và đơn hàng hiện tại
                User currentUser = userService.getCurrentUser();
                Order order = orderService.getCurrentOrder();
                Integer productId = request.getProductId();
                Integer productVariationId = request.getProductVariationId();

                // Kiểm tra xem chi tiết đơn hàng hiện tại đã tồn tại chưa
                OrderDetail orderDetail = orderDetailRepository
                                .getOrderDetailByOrderIdAndProductIdOrProductVariationId(order.getId(), productId,
                                                productVariationId);

                if (orderDetail != null) {
                        // Nếu tồn tại, cập nhật số lượng
                        orderDetail.setQuantity(orderDetail.getQuantity() + request.getQuantity());
                } else {
                        // Nếu không tồn tại, tạo mới
                        orderDetail = orderMapper.toOrderDetail(request);

                        // Gán thông tin sản phẩm vào chi tiết đơn hàng
                        Product product = productRepository.findById(productId)
                                        .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
                        orderDetail.setProduct(product);

                        // Gán thông tin biến thể sản phẩm (nếu có)
                        if (productVariationId != null) {
                                ProductVariation productVariation = productVariationRepository
                                                .findById(productVariationId)
                                                .orElseThrow(() -> new AppException(
                                                                ErrorCode.PRODUCT_VARIATION_NOT_FOUND));
                                orderDetail.setProductVariation(productVariation);
                        }

                        // Gán thông tin đơn hàng
                        orderDetail.setOrder(order);
                }

                // Lưu chi tiết đơn hàng vào cơ sở dữ liệu
                orderDetailRepository.save(orderDetail);

                // Tạo và trả về phản hồi `OrderDetailResponse`
                OrderDetailResponse response = orderMapper.toOrderDetailResponse(orderDetail);
                ProductResponse productResponse = productMapper.toProductResponse(orderDetail.getProduct());
                response.setProduct(productResponse);

                // Gán thông tin biến thể sản phẩm (nếu có) vào phản hồi
                if (orderDetail.getProductVariation() != null) {
                        ProductVariationResponse productVariationResponse = productVariationService
                                        .mapToVariationResponse(orderDetail.getProductVariation().getId());
                        response.setProductVariation(productVariationResponse);
                }

                return response;
        }

}
