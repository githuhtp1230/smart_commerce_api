package com.shop.smart_commerce_api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
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

    @Column(name = "image", length = 500)
    private String image;

    @OneToMany(mappedBy = "productVariation")
    private Set<ProductVariationAttribute> productVariationAttributes = new LinkedHashSet<>();

}