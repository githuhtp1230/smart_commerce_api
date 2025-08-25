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
        LocalDateTime endDate = null;
        LocalDateTime previousStart = null;
        LocalDateTime previousEnd = null;

        if (request.getCategory() != null) {
            endDate = LocalDateTime.now();
            startDate = switch (request.getCategory()) {
                case WEEKLY -> endDate.minus(7, ChronoUnit.DAYS);
                case MONTHLY -> endDate.minus(1, ChronoUnit.MONTHS);
                case YEARLY -> endDate.minus(1, ChronoUnit.YEARS);
            };

            previousEnd = startDate;
            previousStart = switch (request.getCategory()) {
                case WEEKLY -> startDate.minus(7, ChronoUnit.DAYS);
                case MONTHLY -> startDate.minus(1, ChronoUnit.MONTHS);
                case YEARLY -> startDate.minus(1, ChronoUnit.YEARS);
            };
        }

        List<User> users = userRepository.findUsersByDateRange(
                startDate != null ? startDate.toInstant(ZoneOffset.UTC) : null,
                endDate != null ? endDate.toInstant(ZoneOffset.UTC) : null);
        List<User> previousUsers = userRepository.findUsersByDateRange(
                previousStart != null ? previousStart.toInstant(ZoneOffset.UTC) : null,
                previousEnd != null ? previousEnd.toInstant(ZoneOffset.UTC) : null);

        int currentTotal = users.size();
        int previousTotal = previousUsers.size();

        final double percentVariation = previousTotal > 0
                ? ((double) (currentTotal - previousTotal) / previousTotal) * 100
                : 0.0;

        return TotalStatisticResponse.builder()
                .total(currentTotal)
                .percentVariation(percentVariation)
                .build();
    }
}
