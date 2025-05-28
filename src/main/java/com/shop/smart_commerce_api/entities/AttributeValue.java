package com.shop.smart_commerce_api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "attribute_values", schema = "smart_commerce")
public class AttributeValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attribute_id")
    private Attribute attribute;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @OneToMany(mappedBy = "attributeValue")
    private Set<ProductAttribute> productAttributes = new LinkedHashSet<>();

    @OneToMany(mappedBy = "attributeValue")
    private Set<ProductVariationAttribute> productVariationAttributes = new LinkedHashSet<>();

}