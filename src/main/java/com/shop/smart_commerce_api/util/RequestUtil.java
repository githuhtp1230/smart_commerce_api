package com.shop.smart_commerce_api.util;

import jakarta.servlet.http.HttpServletRequest;

public class RequestUtil {

    public static String getIpAddress(HttpServletRequest request) {
        String xForwardedForHeader = request.getHeader("X-Forwarded-For");
        if (xForwardedForHeader == null) {
            var remoteAddr = request.getRemoteAddr();
            if (remoteAddr == null) {
                remoteAddr = "127.0.0.1";
            }

            return remoteAddr;
        }

        return xForwardedForHeader.split(",")[0].trim();
    }
}
