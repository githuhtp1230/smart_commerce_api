package com.shop.smart_commerce_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> {
    private int currentPage;
    private int totalPages;
    private int limit;
    private int totalElements;
    private boolean isLast;

    private List<T> data = new ArrayList<>();
}
