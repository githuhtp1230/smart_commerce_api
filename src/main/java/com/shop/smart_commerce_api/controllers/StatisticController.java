package com.shop.smart_commerce_api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.smart_commerce_api.dto.request.statistic.TotalStatisticRequest;
import com.shop.smart_commerce_api.dto.response.ApiResponse;
import com.shop.smart_commerce_api.dto.response.statistic.TotalStatisticResponse;
import com.shop.smart_commerce_api.services.StatisticService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticController {
    private final StatisticService statisticService;

    @PostMapping("/user-participation")
    public ApiResponse<TotalStatisticResponse> getTotalUserParticipation(
            @RequestBody TotalStatisticRequest request) {
        return ApiResponse.<TotalStatisticResponse>builder()
                .data(statisticService.getTotalUserParticipation(request))
                .message("Total user participation")
                .build();
    }

    @PostMapping("/product-revenue")
    public ApiResponse<TotalStatisticResponse> getProductRevenue(
            @RequestBody TotalStatisticRequest request) {
        return ApiResponse.<TotalStatisticResponse>builder()
                .data(statisticService.getTotalProductsSold(request))
                .message("Total product revenue")
                .build();
    }

    @PostMapping("/products-sold")
    public ApiResponse<TotalStatisticResponse> getTotalProductsSold(
            @RequestBody TotalStatisticRequest request) {
        return ApiResponse.<TotalStatisticResponse>builder()
                .data(statisticService.getTotalProductsSold(request))
                .message("Total products sold")
                .build();
    }

    @PostMapping("/order-total")
    public ApiResponse<TotalStatisticResponse> getTotalOrder(@RequestBody TotalStatisticRequest request) {
        return ApiResponse.<TotalStatisticResponse>builder()
                .data(statisticService.getTotalOrders(request))
                .message("Total order")
                .build();
    }
}
