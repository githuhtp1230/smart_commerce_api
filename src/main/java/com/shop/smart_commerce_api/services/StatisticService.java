package com.shop.smart_commerce_api.services;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Service;

import com.shop.smart_commerce_api.constant.TotalStatisticCategory;
import com.shop.smart_commerce_api.dto.request.statistic.TotalStatisticRequest;
import com.shop.smart_commerce_api.dto.response.statistic.TotalStatisticResponse;
import com.shop.smart_commerce_api.entities.User;
import com.shop.smart_commerce_api.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StatisticService {
    private final UserRepository userRepository;

    public TotalStatisticResponse getTotalUserParticipation(TotalStatisticRequest request) {
        LocalDateTime startDate = null;
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime previousStart = null;
        LocalDateTime previousEnd = null;

        if (request.getCategory() != null) {
            switch (request.getCategory()) {
                case DAY -> {
                    startDate = endDate.minusDays(1);
                    previousEnd = startDate;
                    previousStart = startDate.minusDays(1);
                }
                case WEEK -> {
                    startDate = endDate.minusWeeks(1);
                    previousEnd = startDate;
                    previousStart = startDate.minusWeeks(1);
                }
                case MONTH -> {
                    startDate = endDate.minusMonths(1);
                    previousEnd = startDate;
                    previousStart = startDate.minusMonths(1);
                }
                case YEAR -> {
                    startDate = endDate.minusYears(1);
                    previousEnd = startDate;
                    previousStart = startDate.minusYears(1);
                }
            }
        }

        // Lấy user trong khoảng thời gian hiện tại
        int currentTotal = userRepository.findUsersByDateRange(
                startDate != null ? startDate.toInstant(ZoneOffset.UTC) : null,
                endDate.toInstant(ZoneOffset.UTC)).size();

        // Lấy user trong khoảng thời gian trước đó
        int previousTotal = userRepository.findUsersByDateRange(
                previousStart != null ? previousStart.toInstant(ZoneOffset.UTC) : null,
                previousEnd != null ? previousEnd.toInstant(ZoneOffset.UTC) : null).size();

        double percentVariation = previousTotal > 0
                ? ((double) (currentTotal - previousTotal) / previousTotal) * 100
                : 0.0;

        return TotalStatisticResponse.builder()
                .total(currentTotal)
                .percentVariation(percentVariation)
                .build();
    }
}
