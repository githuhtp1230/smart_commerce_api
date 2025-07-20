package com.shop.smart_commerce_api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.shop.smart_commerce_api.entities.Category;

import io.lettuce.core.dynamic.annotation.Param;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
        Category findByNameAndIsDeletedIsFalse(String name);

        @Query("""
                            SELECT c FROM Category c
                            WHERE (:isDeleted IS NULL OR c.isDeleted = :isDeleted) AND c.parent IS NULL
                        """)
        List<Category> findParentCategories(@Param("isDeleted") Boolean isDeleted);
}
