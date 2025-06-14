package com.shop.smart_commerce_api.services;

import org.springframework.stereotype.Service;
import com.shop.smart_commerce_api.repositories.AttributeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttributeService {
    private AttributeRepository attributeRepository;
}
