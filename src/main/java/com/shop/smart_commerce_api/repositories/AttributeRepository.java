package com.shop.smart_commerce_api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shop.smart_commerce_api.entities.Attribute;
import com.shop.smart_commerce_api.entities.Category;

import io.lettuce.core.dynamic.annotation.Param;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute, Integer> {
        Optional<Attribute> findByName(String name);

        Attribute findByNameAndIsDeletedIsFalse(String name);

        @Query("""
                            SELECT a FROM Attribute a
                            WHERE (:isDeleted IS NULL OR a.isDeleted = :isDeleted)
                        """)
        List<Attribute> findAttributes(@Param("isDeleted") Boolean isDeleted);

        List<Attribute> findByIsDeleted(Boolean isDeleted);
}
