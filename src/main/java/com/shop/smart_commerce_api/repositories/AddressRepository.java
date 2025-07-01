package com.shop.smart_commerce_api.repositories;

import com.shop.smart_commerce_api.entities.Address;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

}
