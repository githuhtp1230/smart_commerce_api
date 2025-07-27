package com.shop.smart_commerce_api.repositories;

import com.shop.smart_commerce_api.entities.Address;

import io.lettuce.core.dynamic.annotation.Param;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    List<Address> findByUserId(Integer userId);

    @Modifying
    @Query("UPDATE Address a SET a.isDefault = false WHERE a.user.id = :userId AND a.id <> :addressId")
    void resetOtherDefaults(@Param("userId") Integer userId, @Param("addressId") Integer addressId);

}
