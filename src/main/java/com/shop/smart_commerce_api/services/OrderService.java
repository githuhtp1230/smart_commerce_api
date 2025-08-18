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
import com.shop.smart_commerce_api.entities.Order;
import com.shop.smart_commerce_api.entities.Payment;
import com.shop.smart_commerce_api.entities.Product;
import com.shop.smart_commerce_api.entities.User;
import com.shop.smart_commerce_api.exception.AppException;
import com.shop.smart_commerce_api.exception.ErrorCode;
import com.shop.smart_commerce_api.mapper.OrderMapper;
import com.shop.smart_commerce_api.mapper.ProductMapper;
import com.shop.smart_commerce_api.repositories.OrderRepository;
import com.shop.smart_commerce_api.repositories.PaymentRepository;
import com.shop.smart_commerce_api.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.var;

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

    public PageResponse<OrderSummaryResponse> getOrderSummaries(String status, int page, int limit) {
        User currentUser = userService.getCurrentUser();
        Integer userId = currentUser.getId();

        Pageable pageable = PageRequest.of(page, limit);
        Page<Order> orderPage;

        if (status == null || status.isEmpty()) {
            orderPage = orderRepository.findByUserId(userId, pageable);
        } else {
            orderPage = orderRepository.findByStatusAndUserId(status.toLowerCase(), userId, pageable);
        }

        var res = orderPage.getContent().stream()
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


}
