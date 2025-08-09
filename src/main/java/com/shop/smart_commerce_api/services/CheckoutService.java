package com.shop.smart_commerce_api.services;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.smart_commerce_api.constant.OrderStatus;
import com.shop.smart_commerce_api.dto.request.checkout.CheckoutRequest;
import com.shop.smart_commerce_api.dto.request.payment.InitPaymentRequest;
import com.shop.smart_commerce_api.dto.response.cart.CartItemResponse;
import com.shop.smart_commerce_api.dto.response.checkout.CheckoutResponse;
import com.shop.smart_commerce_api.dto.response.payment.InitPaymentResponse;
import com.shop.smart_commerce_api.entities.Address;
import com.shop.smart_commerce_api.entities.Order;
import com.shop.smart_commerce_api.entities.OrderDetail;
import com.shop.smart_commerce_api.entities.Payment;
import com.shop.smart_commerce_api.entities.ProductVariation;
import com.shop.smart_commerce_api.entities.Promotion;
import com.shop.smart_commerce_api.entities.User;
import com.shop.smart_commerce_api.exception.AppException;
import com.shop.smart_commerce_api.exception.ErrorCode;
import com.shop.smart_commerce_api.repositories.AddressRepository;
import com.shop.smart_commerce_api.repositories.CartDetailRepository;
import com.shop.smart_commerce_api.repositories.OrderDetailRepository;
import com.shop.smart_commerce_api.repositories.OrderRepository;
import com.shop.smart_commerce_api.repositories.PaymentRepository;
import com.shop.smart_commerce_api.repositories.ProductRepository;
import com.shop.smart_commerce_api.repositories.ProductVariationRepository;
import com.shop.smart_commerce_api.util.DateUtil;
import com.shop.smart_commerce_api.util.RequestUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CheckoutService {
        private final OrderRepository orderRepository;
        private final CartDetailRepository cartDetailRepository;
        private final AddressRepository addressRepository;
        private final PaymentRepository paymentRepository;
        private final OrderDetailRepository orderDetailRepository;
        private final UserService userService;
        private final OrderDetailService orderDetailService;
        private final VnPayService vnPayService;
        private final CryptoService cryptoService;

        @Transactional
        public CheckoutResponse checkoutTransfer(CheckoutRequest request) {
                if (request.getPaymentId() == null || request.getCartItemIds() == null
                                || request.getCartItemIds().isEmpty()) {
                        throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
                }

                Payment payment = paymentRepository.findById(request.getPaymentId())
                                .orElseThrow(() -> new AppException(ErrorCode.PAYMENT_NOT_FOUND));
                User currentUser = userService.getCurrentUser();

                Address address = addressRepository.findDefaultByUserId(currentUser.getId())
                                .orElseThrow(() -> new AppException(ErrorCode.ADDRESS_NOT_FOUND));

                List<CartItemResponse> cartItemResponses = cartDetailRepository.getCartItemsWithListIds(
                                currentUser.getId(), request.getCartItemIds());

                if (cartItemResponses.isEmpty()) {
                        throw new AppException(ErrorCode.CART_ITEMS_NOT_FOUND);
                }

                Order order = Order.builder()
                                .user(currentUser)
                                .address(String.format("%s, %s, %s, %s",
                                                address.getStreetAddress(), address.getWard(), address.getDistrict(),
                                                address.getProvince()))
                                .payment(payment)
                                .createdAt(LocalDateTime.now())
                                .status(OrderStatus.CONFIRMED)
                                .build();

                order = orderRepository.save(order);

                List<OrderDetail> orderDetails = orderDetailService
                                .mapToOrderDetailsFromCartItemResponses(cartItemResponses, order);

                long total = calculateTotal(orderDetails);
                order.setTotal(total);
                orderRepository.save(order);

                orderDetailRepository.saveAll(orderDetails);

                InitPaymentRequest initPaymentRequest = InitPaymentRequest.builder()
                                .amount(total)
                                .ipAddress(request.getIpAddress())
                                .userId(currentUser.getId())
                                .txnRef(String.valueOf(order.getId()))
                                .build();

                InitPaymentResponse initPaymentResponse = vnPayService.init(initPaymentRequest);

                return CheckoutResponse.builder()
                                .payment(initPaymentResponse)
                                .build();
        }

        private long calculateTotal(List<OrderDetail> orderDetails) {
                return orderDetails.stream()
                                .mapToLong(od -> {
                                        Promotion promotion = od.getProduct().getPromotion();
                                        if (promotion != null && DateUtil.isCurrentDateInRange(
                                                        promotion.getStartDate(), promotion.getEndDate())) {
                                                double discountPercent = promotion.getDiscountValuePercent() / 100.0;
                                                double original = od.getPrice();
                                                double discountAmount = original - (original * discountPercent);
                                                return (long) Math.floor(discountAmount);
                                        }
                                        return od.getPrice();
                                })
                                .sum();
        }

        public boolean verifySignature(Map<String, String> fields, String secureHash, String secureHashType) {
                try {
                        // Lọc bỏ các tham số không dùng để tạo chữ ký
                        Map<String, String> sortedFields = new TreeMap<>();
                        for (Map.Entry<String, String> entry : fields.entrySet()) {
                                String key = entry.getKey();
                                String value = entry.getValue();
                                if (key.equals("vnp_SecureHash") || key.equals("vnp_SecureHashType") || value == null
                                                || value.isEmpty()) {
                                        continue;
                                }
                                sortedFields.put(key, value);
                        }

                        // Tạo chuỗi dữ liệu: key=value nối bằng &
                        StringBuilder sb = new StringBuilder();
                        for (Map.Entry<String, String> entry : sortedFields.entrySet()) {
                                if (sb.length() > 0) {
                                        sb.append("&");
                                }
                                sb.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(),
                                                StandardCharsets.US_ASCII.toString()));
                        }
                        String data = sb.toString();

                        // Tạo chữ ký HMAC SHA512 dùng CryptoService
                        String calculatedHash = cryptoService.sign(data);

                        // So sánh chữ ký
                        return calculatedHash.equalsIgnoreCase(secureHash);

                } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                }
        }

        @Transactional
        public CheckoutResponse checkoutCash(CheckoutRequest request) {
                Address address = addressRepository.findById(request.getAddressId())
                                .orElseThrow(() -> new AppException(ErrorCode.ADDRESS_NOT_FOUND));
                Payment payment = paymentRepository.findById(request.getPaymentId())
                                .orElseThrow(() -> new AppException(ErrorCode.PAYMENT_NOT_FOUND));
                User currentUser = userService.getCurrentUser();

                List<CartItemResponse> cartItemResponses = cartDetailRepository.getCartItemsWithListIds(
                                currentUser.getId(),
                                request.getCartItemIds());

                if (cartItemResponses.size() <= 0) {
                        throw new AppException(ErrorCode.CART_ITEMS_NOT_FOUND);
                }

                Order order = Order.builder()
                                .user(currentUser)
                                .address(address.toString())
                                .payment(payment)
                                .status(OrderStatus.CONFIRMED)
                                .build();

                order = orderRepository.save(order);

                List<OrderDetail> orderDetails = orderDetailService
                                .mapToOrderDetailsFromCartItemResponses(cartItemResponses, order);

                long total = orderDetails.stream()
                                .mapToLong(od -> od.getPrice() * od.getQuantity())
                                .sum();

                order.setTotal(total);
                orderRepository.save(order);

                orderDetailRepository.saveAll(orderDetails);

                InitPaymentRequest initPaymentRequest = InitPaymentRequest.builder()
                                .amount(total)
                                .ipAddress(request.getIpAddress())
                                .userId(currentUser.getId())
                                .txnRef(String.valueOf(order.getId()))
                                .build();

                var initPaymentResponse = vnPayService.init(initPaymentRequest);

                cartDetailRepository.deleteAllById(request.getCartItemIds());

                return CheckoutResponse.builder()
                                .payment(initPaymentResponse)
                                .build();
        }
}
