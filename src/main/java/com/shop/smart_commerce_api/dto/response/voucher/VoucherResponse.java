package com.shop.smart_commerce_api.dto.response.voucher;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoucherResponse {
    private Integer id;
    private String code;
    private Integer discountAmount;
    private String description;
    private String startDate;
    private String endDate;

    public VoucherResponse(Integer id, String code, Integer discountAmount) {
        this.id = id;
        this.code = code;
        this.discountAmount = discountAmount;
    }
}
