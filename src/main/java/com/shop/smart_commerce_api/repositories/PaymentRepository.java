package com.shop.smart_commerce_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shop.smart_commerce_api.entities.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

}
