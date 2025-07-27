package com.shop.smart_commerce_api.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "address", schema = "smart_commerce")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Size(max = 50)
    @NotNull
    @Column(name = "province", nullable = false, length = 50)
    private String province;

    @Size(max = 50)
    @NotNull
    @Column(name = "district", nullable = false, length = 50)
    private String district;

    @Size(max = 50)
    @NotNull
    @Column(name = "ward", nullable = false, length = 50)
    private String ward;

    @Size(max = 50)
    @Column(name = "street_address", length = 50)
    private String streetAddress;

    @Column(name = "is_default")
    @ColumnDefault("false")
    private Boolean isDefault = false;

    @ColumnDefault("0")
    @Column(name = "is_delete")
    private Boolean isDelete;

}