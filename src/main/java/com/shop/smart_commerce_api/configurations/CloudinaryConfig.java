package com.shop.smart_commerce_api.configurations;

import java.util.HashMap;

import com.cloudinary.Cloudinary;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "dj0gwqn1j");
        config.put("api_key", "663231235547731");
        config.put("api_secret", "yd2bvB0Nwo-k3l8p1YoJtLKDlMc");
        return new Cloudinary(config);
    }
}
