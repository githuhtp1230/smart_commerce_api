package com.shop.smart_commerce_api.configurations;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.shop.smart_commerce_api.entities.Role;
import com.shop.smart_commerce_api.entities.User;
import com.shop.smart_commerce_api.repositories.RoleRepository;
import com.shop.smart_commerce_api.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AppInitConfig {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private String[] userNames = { "phuong", "thien", "thanh", "nhat", "phu" };

    @Bean
    ApplicationRunner applicationRunner() {
        return args -> {
            Role role = roleRepository.findByName("ADMIN");
            if (role == null) {
                role = new Role();
                role.setName("ADMIN");
                role = roleRepository.save(role);
            }
            for (String name : userNames) {
                createUser(name, role);
            }
        };
    }

    private void createUser(String name, Role role) {
        if (userRepository.findByName(name) == null) {
            User user = User.builder()
                    .name(name)
                    .email(name + "@gmail.com")
                    .role(role)
                    .password(passwordEncoder.encode("123"))
                    .build();
            userRepository.save(user);
        }
    }
}
