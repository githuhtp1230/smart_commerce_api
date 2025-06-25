package com.shop.smart_commerce_api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "products", schema = "smart_commerce")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

    @Column(name = "name", length = 200)
    private String name;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "stock")
    private Integer stock;

    @ColumnDefault("(now())")
    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "is_deleted")
    private Integer isDeleted;

    @OneToMany(mappedBy = "product")
    private Set<AttributeValueDetail> attributeValueDetails = new LinkedHashSet<>();

    @OneToMany(mappedBy = "product")
    private Set<CartDetail> cartDetails = new LinkedHashSet<>();

    @OneToMany(mappedBy = "product")
    private Set<OrderDetail> orderDetails = new LinkedHashSet<>();

    @OneToMany(mappedBy = "product")
    private Set<ProductVariation> productVariations = new LinkedHashSet<>();

    @OneToMany(mappedBy = "product")
    private Set<Review> reviews = new LinkedHashSet<>();

}