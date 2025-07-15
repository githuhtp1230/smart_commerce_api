package com.shop.smart_commerce_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.shop.smart_commerce_api.configurations.VnPayProperties;

@SpringBootApplication
@EnableConfigurationProperties(VnPayProperties.class)
public class SmartCommerceApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartCommerceApiApplication.class, args);
		
	}

}
