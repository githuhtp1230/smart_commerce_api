package com.shop.smart_commerce_api.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "product_variations", schema = "smart_commerce")
public class ProductVariation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "price")
    private Integer price;

    @Column(name = "stock")
    private Integer stock;

    @ColumnDefault("0")
    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @OneToMany(mappedBy = "productVariation")
    private Set<ProductVariationAttribute> productVariationAttributes = new LinkedHashSet<>();

}