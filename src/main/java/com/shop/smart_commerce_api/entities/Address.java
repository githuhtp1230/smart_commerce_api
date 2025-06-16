package com.shop.smart_commerce_api.entities;

import jakarta.persistence.*;
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "province", nullable = false, length = 50)
    private String province;

    @Column(name = "district", nullable = false, length = 50)
    private String district;

    @Column(name = "ward", nullable = false, length = 50)
    private String ward;

    @Column(name = "street_address")
    private Integer streetAddress;

    @ColumnDefault("0")
    @Column(name = "is_delete")
    private Boolean isDelete;

}