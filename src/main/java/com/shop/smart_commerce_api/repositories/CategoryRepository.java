package com.shop.smart_commerce_api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.shop.smart_commerce_api.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findByNameAndIsDeletedIsFalse(String name);
    List<Category> findCategoriesByIsDeletedIsFalseAndParentIsNull();
}
