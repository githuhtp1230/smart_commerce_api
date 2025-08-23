package com.shop.smart_commerce_api.services;

import com.shop.smart_commerce_api.dto.request.order.CategoryStatisticalRequest;
import com.shop.smart_commerce_api.dto.request.order.ProductStatisticalRequest;
import com.shop.smart_commerce_api.dto.response.order.ProductStatisticalResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import com.shop.smart_commerce_api.dto.response.order.StatisticalResponse;
import com.shop.smart_commerce_api.dto.request.order.StatisticalRequest;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.shop.smart_commerce_api.constant.OrderStatus;
import com.shop.smart_commerce_api.dto.response.PageResponse;
import com.shop.smart_commerce_api.dto.response.order.CategoryStatisticalResponse;
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

    private boolean isValidStatus(String status) {
        return OrderStatus.CONFIRMED.equalsIgnoreCase(status)
                || OrderStatus.CANCELLED.equalsIgnoreCase(status)
                || OrderStatus.SHIPPING.equalsIgnoreCase(status)
                || OrderStatus.DELIVERED.equalsIgnoreCase(status);
    }

    public OrderResponse updateOrderStatus(Integer orderId, String status) {
        if (!isValidStatus(status)) {
            ErrorCode code;
            try {
                code = ErrorCode.INVALID_ORDER_STATUS;
            } catch (Exception e) {
                code = ErrorCode.INTERNAL_SERVER_ERROR;
            }
            throw new AppException(code);
        }
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        order.setStatus(status.toLowerCase());
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toOrderResponse(savedOrder);
    }

    public OrderResponse cancelOrderByUser(Integer orderId) {
        User currentUser = userService.getCurrentUser();
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        order.setStatus(OrderStatus.CANCELLED);
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toOrderResponse(savedOrder);
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
                }).collect(Collectors.toList());

        return PageResponse.<OrderSummaryResponse>builder().currentPage(orderPage.getNumber() + 1)
                .totalPages(orderPage.getTotalPages()).limit(orderPage.getSize())
                .totalElements((int) orderPage.getTotalElements()).isLast(orderPage.isLast()).data(res).build();

    }

    private LocalDateTime getFromDate(String time) {
        if (time == null)
            return null;
        LocalDateTime now = LocalDateTime.now();
        switch (time.toLowerCase()) {
            case "week":
                return now.minusWeeks(1);
            case "month":
                return now.minusMonths(1);
            case "year":
                return now.minusYears(1);
            default:
                return null;
        }
    }

    private int safeInt(Object value) {
        return value != null ? ((Number) value).intValue() : 0;
    }

    private double safeDouble(Object value) {
        return value != null ? ((Number) value).doubleValue() : 0.0;
    }

    private Object[] safeStats(Object statsObj) {
        return (statsObj instanceof Object[]) ? (Object[]) statsObj : new Object[] { 0, 0 };
    }

    public StatisticalResponse getUserOrderStatistics(Integer userId, StatisticalRequest request) {
        String time = request != null ? request.getTime() : null;
        LocalDateTime from = getFromDate(time);

        Object[] stats = safeStats(orderRepository.getUserOrderStatistics(userId, from));

        return StatisticalResponse.builder()
                .totalProducts(safeInt(stats[0]))
                .totalAmount(safeDouble(stats[1]))
                .time(time != null ? time : "all")
                .build();
    }

    public ProductStatisticalResponse getProductStatistical(ProductStatisticalRequest request) {
        String time = request != null ? request.getTime() : null;
        Integer productId = request != null ? request.getProductId() : null;
        LocalDateTime from = getFromDate(time);

        Object[] stats = safeStats(orderRepository.getProductStatistical(productId, from));

        return ProductStatisticalResponse.builder()
                .totalSold(safeInt(stats[0]))
                .totalRevenue(safeDouble(stats[1]))
                .time(time != null ? time : "all")
                .productId(productId)
                .build();
    }

    public CategoryStatisticalResponse getCategoryStatistical(CategoryStatisticalRequest request) {
        String time = request != null ? request.getTime() : null;
        Integer categoryId = request != null ? request.getCategoryId() : null;
        LocalDateTime from = getFromDate(time);

        Object[] stats = safeStats(orderRepository.getCategoryStatistical(categoryId, from));

        return CategoryStatisticalResponse.builder()
                .totalSold(safeInt(stats[0]))
                .totalRevenue(safeDouble(stats[1]))
                .time(time != null ? time : "all")
                .categoryId(categoryId)
                .build();
    }

}
