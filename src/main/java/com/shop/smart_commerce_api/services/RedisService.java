package com.shop.smart_commerce_api.services;

import java.time.Duration;
import java.util.Map;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.shop.smart_commerce_api.model.ForgotPassword;
import com.shop.smart_commerce_api.model.OtpSession;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisTemplate<String, Object> objectRedisTemplate;

    public void saveObject(String key, Object object, long ttlMinutes) {
        objectRedisTemplate.opsForValue().set(key, object, Duration.ofMinutes(ttlMinutes));
    }

    public void deleteObject(String key) {
        objectRedisTemplate.delete(key);
    }

    public Object getObject(String key) {
        Object object = objectRedisTemplate.opsForValue().get(key);
        return object;
    }
}
