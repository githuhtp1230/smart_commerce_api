package com.shop.smart_commerce_api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.smart_commerce_api.dto.request.statistic.TotalStatisticRequest;
import com.shop.smart_commerce_api.dto.response.ApiResponse;
import com.shop.smart_commerce_api.dto.response.statistic.TotalStatisticResponse;
import com.shop.smart_commerce_api.services.StatisticService;
import com.shop.smart_commerce_api.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticController {
    private final StatisticService statisticService;

    @GetMapping("/user-participation")
    public ApiResponse<TotalStatisticResponse> getTotalUserParticipation(
            @RequestBody TotalStatisticRequest request) {
        return ApiResponse.<TotalStatisticResponse>builder()
                .data(statisticService.getTotalUserParticipation(request))
                .message("Total user participation")
                .build();
    }
}
