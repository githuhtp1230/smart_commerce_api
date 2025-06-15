package com.shop.smart_commerce_api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "attributes", schema = "smart_commerce")
public class Attribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", length = 100)
    private String name;

    @ColumnDefault("0")
    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @OneToMany(mappedBy = "attribute")
    private Set<AttributeValue> attributeValues = new LinkedHashSet<>();

}