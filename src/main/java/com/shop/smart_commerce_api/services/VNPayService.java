package com.shop.smart_commerce_api.services;

import com.shop.smart_commerce_api.configurations.VnPayProperties;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class VNPayService {

    private final VnPayProperties vnPayProps;

    public VNPayService(VnPayProperties vnPayProps) {
        this.vnPayProps = vnPayProps;
        System.out.println(" VNPay Config loaded: " + vnPayProps.getTmnCode());
    }

    public String createPaymentUrl(HttpServletRequest req, int amount, String orderId) throws UnsupportedEncodingException {
        if (vnPayProps.getTmnCode() == null || vnPayProps.getSecretKey() == null || vnPayProps.getInitPaymentUrl() == null) {
            throw new IllegalStateException("Cấu hình VNPAY bị thiếu. Vui lòng kiểm tra application.yaml");
        }

        String vnp_TxnRef = orderId;
        String vnp_OrderInfo = "Thanh toán đơn hàng " + orderId;
        String vnp_Amount = String.valueOf(amount * 100);
        String vnp_IpAddr = req.getRemoteAddr();
        String vnp_CreateDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", "2.1.0");
        vnp_Params.put("vnp_Command", "pay");
        vnp_Params.put("vnp_TmnCode", vnPayProps.getTmnCode());
        vnp_Params.put("vnp_Amount", vnp_Amount);
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", "other");
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", vnPayProps.getReturnUrl());
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);

        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();

        for (int i = 0; i < fieldNames.size(); i++) {
            String fieldName = fieldNames.get(i);
            String value = vnp_Params.get(fieldName);
            if (value != null && !value.isEmpty()) {
                hashData.append(fieldName).append('=').append(URLEncoder.encode(value, StandardCharsets.UTF_8));
                query.append(fieldName).append('=').append(URLEncoder.encode(value, StandardCharsets.UTF_8));
                if (i < fieldNames.size() - 1) {
                    hashData.append('&');
                    query.append('&');
                }
            }
        }

        String secureHash = hmacSHA512(vnPayProps.getSecretKey(), hashData.toString());
        query.append("&vnp_SecureHash=").append(secureHash);

        return vnPayProps.getInitPaymentUrl() + "?" + query.toString();
    }

    private String hmacSHA512(String key, String data) {
        try {
            Mac hmac512 = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
            hmac512.init(secretKeySpec);
            byte[] hash = hmac512.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hash);
        } catch (Exception ex) {
            throw new RuntimeException("Lỗi mã hóa HMAC", ex);
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02X", b));
        }
        return result.toString();
    }
}
