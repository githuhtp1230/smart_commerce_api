package com.shop.smart_commerce_api.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.shop.smart_commerce_api.dto.response.order.OrderResponse;
import com.shop.smart_commerce_api.entities.Order;
import com.shop.smart_commerce_api.entities.Payment;
import com.shop.smart_commerce_api.entities.User;
import com.shop.smart_commerce_api.exception.AppException;
import com.shop.smart_commerce_api.exception.ErrorCode;
import com.shop.smart_commerce_api.mapper.OrderMapper;
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

    public List<OrderResponse> getOrdersByStatus(Boolean status, Pageable pageable) {
        Page<Order> orderPage = orderRepository.findAll(pageable);

        // Lọc các đơn hàng theo trạng thái
        return orderPage.getContent().stream()
                .filter(order -> status == null || order.getStatus().equals(status))
                .map(orderMapper::toOrderResponse)
                .collect(Collectors.toList());
    }
}
