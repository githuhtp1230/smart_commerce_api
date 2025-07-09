package com.shop.smart_commerce_api.exception;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

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

    CATEGORY_EXISTS(HttpStatus.CONFLICT, "Category is exists"),
    USER_EXSITS(HttpStatus.CONFLICT, "User is exists"),

    EMAIL_OR_PASSWORD_INVALID(HttpStatus.BAD_REQUEST, "Email or password is invalid"),

    VERIFY_OTP_FAILED(HttpStatus.BAD_REQUEST, "Verify otp failed"),
    OTP_WRONG(HttpStatus.BAD_REQUEST, "Otp is wrong"),
    INVALID_OTP(HttpStatus.BAD_REQUEST, "Invalid otp"),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "Wrong current password"),

    ACCESS_DENIED(HttpStatus.FORBIDDEN, "You have not permission"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Unauthorized"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");

    private HttpStatusCode statusCode;
    private String message;
}
