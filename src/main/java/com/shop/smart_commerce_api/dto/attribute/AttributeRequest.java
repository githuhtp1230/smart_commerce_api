package com.shop.smart_commerce_api.dto.attribute;

import java.util.LinkedHashSet;
import java.util.Set;

import com.shop.smart_commerce_api.entities.AttributeValue;

import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttributeRequest {
    private String name;
}
