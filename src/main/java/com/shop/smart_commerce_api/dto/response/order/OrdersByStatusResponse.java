package com.shop.smart_commerce_api.dto.response.order;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OrdersByStatusResponse {

    private List<OrderResponse> confirmed;
    private List<OrderResponse> cancelled;
    private List<OrderResponse> shipping;
    private List<OrderResponse> delivered;
}
