package com.shop.smart_commerce_api.exception;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.access.method.P;

import com.shop.smart_commerce_api.entities.Payment;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ErrorCode {
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "Category not found"),
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "Product not found"),
    PRODUCT_VARIATION_NOT_FOUND(HttpStatus.NOT_FOUND, "Product variation not found"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found"),
    ATTRIBUTE_NOT_FOUND(HttpStatus.NOT_FOUND, "Attribute not found"),
    ADDRESS_NOT_FOUND(HttpStatus.NOT_FOUND, "Address not found"),
    CART_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "Cart item not found"),
    ATTRIBUTE_VALUE_NOT_FOUND(HttpStatus.NOT_FOUND, "Attribute value not found"),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "Order not found"),
    PAYMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "Payment not found"),
    CART_ITEMS_NOT_FOUND(HttpStatus.NOT_FOUND, "Cart items not found"),
    PROMOTION_NOT_FOUND(HttpStatus.NOT_FOUND, "Promotion not found"),
    PERMISSION_NOT_FOUND(HttpStatus.NOT_FOUND, "Permission not found"),
    ROLE_NOT_FOUND(HttpStatus.NOT_FOUND, "Role not found"),

    ATTRIBUTE_VALUE_EXISTS(HttpStatus.CONFLICT, "AttributeValue is exists"),
    ATTRIBUTE_EXISTS(HttpStatus.CONFLICT, "Attribute is exists"),
    CATEGORY_EXISTS(HttpStatus.CONFLICT, "Category is exists"),
    USER_EXISTS(HttpStatus.CONFLICT, "User is exists"),
    PROMOTION_EXISTS(HttpStatus.CONFLICT, "Promotion is exists"),

    EMAIL_OR_PASSWORD_INVALID(HttpStatus.BAD_REQUEST, "Email or password is invalid"),

    VERIFY_OTP_FAILED(HttpStatus.BAD_REQUEST, "Verify otp failed"),
    OTP_WRONG(HttpStatus.BAD_REQUEST, "Otp is wrong"),
    INVALID_OTP(HttpStatus.BAD_REQUEST, "Invalid otp"),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "Wrong current password"),

    FILE_UPLOAD_ERROR(HttpStatus.BAD_REQUEST, "File upload error"),

    ORDER_DETAILS_EMPTY(HttpStatus.BAD_REQUEST, "Order details cannot be empty"),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "You have not permission"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Unauthorized"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"),
    PERMISSION_ALREADY_ASSIGNED(HttpStatus.BAD_REQUEST, "Permission already assigned to role"),

    VNPAY_SIGNING_FAILED(HttpStatus.BAD_REQUEST, "Signing vnpay failed");

    private HttpStatusCode statusCode;
    private String message;
}
