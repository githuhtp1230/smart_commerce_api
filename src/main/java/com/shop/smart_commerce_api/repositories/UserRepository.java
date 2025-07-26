package com.shop.smart_commerce_api.repositories;

import com.shop.smart_commerce_api.entities.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    User findByName(String name);

    @Query("SELECT u FROM User u WHERE u.role.name = 'USER'")
    List<User> findAllCustomers();

    @Query("SELECT u FROM User u WHERE u.role.name <> 'USER'")
    List<User> findAllMemberships();
}
