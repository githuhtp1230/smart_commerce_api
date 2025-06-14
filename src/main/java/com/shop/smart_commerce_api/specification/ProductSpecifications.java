package com.shop.smart_commerce_api.specification;

import com.shop.smart_commerce_api.entities.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecifications {
    public static Specification<Product> hasCategoryId(int categoryId) {
        return ((root, query, cb) -> cb.equal(root.get("category").get("id"), categoryId));
    }
}
