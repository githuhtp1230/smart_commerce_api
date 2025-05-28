package com.shop.smart_commerce_api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "vouchers", schema = "smart_commerce")
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "code", nullable = false)
    private Integer code;

    @Lob
    @Column(name = "discount_type")
    private String discountType;

    @Column(name = "discount_value", nullable = false)
    private Integer discountValue;

    @Lob
    @Column(name = "min_order_amount")
    private String minOrderAmount;

    @Column(name = "max_discount")
    private Integer maxDiscount;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(name = "usage_limit")
    private Integer usageLimit;

    @Column(name = "usage_count")
    private Integer usageCount;

    @Column(name = "is_deleted")
    private Integer isDeleted;

    @OneToMany(mappedBy = "voucher")
    private Set<Order> orders = new LinkedHashSet<>();

}