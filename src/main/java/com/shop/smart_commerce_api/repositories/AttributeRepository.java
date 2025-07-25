package com.shop.smart_commerce_api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shop.smart_commerce_api.entities.Attribute;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute, Integer> {
    Optional<Attribute> findByName(String name);
}
