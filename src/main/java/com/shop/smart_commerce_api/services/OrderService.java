package com.shop.smart_commerce_api.services;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.shop.smart_commerce_api.constant.OrderStatus;
import com.shop.smart_commerce_api.dto.response.PageResponse;
import com.shop.smart_commerce_api.dto.response.order.OrderDetailResponse;
import com.shop.smart_commerce_api.dto.response.order.OrderResponse;
import com.shop.smart_commerce_api.dto.response.order.OrderSummaryResponse;
import com.shop.smart_commerce_api.dto.response.order.OrdersByStatusResponse;
import com.shop.smart_commerce_api.dto.response.user.UserResponse;
import com.shop.smart_commerce_api.entities.Order;
import com.shop.smart_commerce_api.entities.Payment;
import com.shop.smart_commerce_api.entities.Product;
import com.shop.smart_commerce_api.entities.User;
import com.shop.smart_commerce_api.exception.AppException;
import com.shop.smart_commerce_api.exception.ErrorCode;
import com.shop.smart_commerce_api.mapper.OrderMapper;
import com.shop.smart_commerce_api.mapper.ProductMapper;
import com.shop.smart_commerce_api.mapper.UserMapper;
import com.shop.smart_commerce_api.repositories.OrderRepository;
import com.shop.smart_commerce_api.repositories.PaymentRepository;
import com.shop.smart_commerce_api.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final OrderMapper orderMapper;
    private final PaymentRepository paymentRepository;
    private final ProductService productService;
    private final ProductVariationService productVariationService;
    private final ProductMapper productMapper;
    private final UserMapper userMapper;

    public PageResponse<OrderSummaryResponse> getAllOrders(String status, int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<Order> orderPage;

        if (status == null || status.isEmpty()) {
            orderPage = orderRepository.findAll(pageable);
        } else {
            orderPage = orderRepository.findByStatus(status.toLowerCase(), pageable);
        }

        List<OrderSummaryResponse> res = orderPage.getContent().stream()
                .map(order -> {
                    OrderSummaryResponse orderSummary = orderMapper.toOrderSummaryResponse(order);

                    List<OrderDetailResponse> detailResponses = order.getOrderDetails().stream()
                            .map(orderDetail -> {
                                OrderDetailResponse detailResponse = orderMapper.toOrderDetailResponse(orderDetail);
                                detailResponse.setProduct(productMapper.toProductResponse(orderDetail.getProduct()));

                                if (orderDetail.getProductVariation() != null) {
                                    detailResponse.setProductVariation(
                                            productVariationService
                                                    .mapToVariationResponse(orderDetail.getProductVariation().getId()));
                                }

                                orderDetail.getProduct().getImageProducts().stream().findFirst()
                                        .ifPresent(img -> detailResponse.setImage(img.getImageUrl()));

                                return detailResponse;
                            })
                            .collect(Collectors.toList());

                    orderSummary.setOrderDetails(detailResponses);

                    order.getOrderDetails().stream().findFirst().ifPresent(orderDetail -> {
                        orderDetail.getProduct().getImageProducts().stream().findFirst()
                                .ifPresent(img -> orderSummary.setProductImage(img.getImageUrl()));
                    });

                    // Map thông tin user
                    if (order.getUser() != null) {
                        UserResponse userResponse = userMapper.toUserResponse(order.getUser());
                        orderSummary.setUserId(userResponse);
                    }

                    return orderSummary;
                })
                .collect(Collectors.toList());

        return PageResponse.<OrderSummaryResponse>builder()
                .currentPage(orderPage.getNumber() + 1)
                .totalPages(orderPage.getTotalPages())
                .limit(orderPage.getSize())
                .totalElements((int) orderPage.getTotalElements())
                .isLast(orderPage.isLast())
                .data(res)
                .build();
    }

    public Order getCurrentOrder() {
        User currentUser = userService.getCurrentUser();
        // Giả định rằng bạn có một phương thức để lấy đơn hàng hiện tại của người dùng
        List<Order> orders = orderRepository.findByUserId(currentUser.getId());
        if (orders.isEmpty()) {
            throw new AppException(ErrorCode.ORDER_NOT_FOUND);
        }
        Order order = orders.get(0); // Lấy đơn hàng đầu tiên hoặc xử lý theo logic của bạn
        return orderMapper.toOrder(order);
    }

    public OrderResponse getOrderById(Integer orderId) {
        return orderRepository.findOrderById(orderId);
    }

    public List<OrderResponse> getMyOrders() {
        User user = userRepository.findById(userService.getCurrentUser().getId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        List<Order> orders = orderRepository.findByUserId(user.getId());
        return orders.stream()
                .map(orderMapper::toOrderResponse)
                .toList();

    }

    public OrderResponse addOrder(Order order) {
        order.setUser(userService.getCurrentUser());
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toOrderResponse(savedOrder);
    }

    public PageResponse<OrderSummaryResponse> getOrderSummaries(String status, int page, int limit, Integer userId) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<Order> orderPage;

        if (status == null || status.isEmpty()) {
            orderPage = orderRepository.findByUserId(userId, pageable);
        } else {
            orderPage = orderRepository.findByStatusAndUserId(status.toLowerCase(), userId, pageable);
        }

        List<OrderSummaryResponse> res = orderPage.getContent().stream()
                .map(order -> {

                    OrderSummaryResponse orderSummary = orderMapper.toOrderSummaryResponse(order);

                    List<OrderDetailResponse> detailResponses = order.getOrderDetails().stream()
                            .map(orderDetail -> {
                                OrderDetailResponse detailResponse = orderMapper.toOrderDetailResponse(orderDetail);
                                detailResponse.setProduct(productMapper.toProductResponse(orderDetail.getProduct()));

                                if (orderDetail.getProductVariation() != null) {
                                    detailResponse.setProductVariation(
                                            productVariationService
                                                    .mapToVariationResponse(orderDetail.getProductVariation().getId()));
                                }

                                orderDetail.getProduct().getImageProducts().stream().findFirst()
                                        .ifPresent(img -> detailResponse.setImage(img.getImageUrl()));

                                return detailResponse;
                            })
                            .collect(Collectors.toList());

                    orderSummary.setOrderDetails(detailResponses);

                    order.getOrderDetails().stream().findFirst().ifPresent(orderDetail -> {
                        orderDetail.getProduct().getImageProducts().stream().findFirst()
                                .ifPresent(img -> orderSummary.setProductImage(img.getImageUrl()));
                    });
                    if (order.getUser() != null) {
                        UserResponse userResponse = userMapper.toUserResponse(order.getUser());
                        orderSummary.setUserId(userResponse);
                    }
                    return orderSummary;
                })
                .collect(Collectors.toList());

        return PageResponse.<OrderSummaryResponse>builder()
                .currentPage(orderPage.getNumber() + 1)
                .totalPages(orderPage.getTotalPages())
                .limit(orderPage.getSize())
                .totalElements((int) orderPage.getTotalElements())
                .isLast(orderPage.isLast())
                .data(res)
                .build();
    }

    public Map<String, Long> getOrderStats() {
        List<Order> orders = orderRepository.findAll();

        Map<String, Long> stats = orders.stream()
                .collect(Collectors.groupingBy(
                        Order::getStatus,
                        Collectors.counting()));

        List<String> allStatuses = List.of(
                OrderStatus.CONFIRMED,
                OrderStatus.CANCELLED,
                OrderStatus.SHIPPING,
                OrderStatus.DELIVERED);

        allStatuses.forEach(status -> stats.putIfAbsent(status, 0L));

        return stats;
    }

    public OrderSummaryResponse updateOrderStatus(Integer orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        String normalized = status.toLowerCase();
        if (!List.of("confirmed", "cancelled", "shipping", "delivered").contains(normalized)) {
            throw new AppException(ErrorCode.ORDER_NOT_FOUND);
        }

        order.setStatus(normalized);
        Order updatedOrder = orderRepository.save(order);

        return mapToSummary(updatedOrder);
    }

    private OrderSummaryResponse mapToSummary(Order order) {
        OrderSummaryResponse orderSummary = orderMapper.toOrderSummaryResponse(order);

        // Map order details
        List<OrderDetailResponse> detailResponses = order.getOrderDetails().stream()
                .map(orderDetail -> {
                    OrderDetailResponse detailResponse = orderMapper.toOrderDetailResponse(orderDetail);
                    detailResponse.setProduct(productMapper.toProductResponse(orderDetail.getProduct()));

                    if (orderDetail.getProductVariation() != null) {
                        detailResponse.setProductVariation(
                                productVariationService
                                        .mapToVariationResponse(orderDetail.getProductVariation().getId()));
                    }

                    orderDetail.getProduct().getImageProducts().stream().findFirst()
                            .ifPresent(img -> detailResponse.setImage(img.getImageUrl()));

                    return detailResponse;
                })
                .toList();

        orderSummary.setOrderDetails(detailResponses);

        // Map ảnh chính (lấy từ sản phẩm đầu tiên)
        order.getOrderDetails().stream().findFirst().ifPresent(orderDetail -> {
            orderDetail.getProduct().getImageProducts().stream().findFirst()
                    .ifPresent(img -> orderSummary.setProductImage(img.getImageUrl()));
        });

        // Map user
        if (order.getUser() != null) {
            UserResponse userResponse = userMapper.toUserResponse(order.getUser());
            orderSummary.setUserId(userResponse);
        }

        return orderSummary;
    }

}
