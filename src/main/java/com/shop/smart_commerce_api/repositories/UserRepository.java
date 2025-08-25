package com.shop.smart_commerce_api.repositories;

import com.shop.smart_commerce_api.entities.User;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    User findByName(String name);

    @Query("SELECT u FROM User u WHERE u.role.name = 'USER'")
    List<User> findAllCustomers();

    @Query("SELECT u FROM User u WHERE u.role.name <> 'USER'")
    List<User> findAllMemberships();

    @Query("SELECT COUNT(u) FROM User u WHERE u.role.name = 'USER'")
    Integer countAllCustomers();

    @Query("SELECT u FROM User u WHERE " +
            "(:startDate IS NULL OR u.createdAt >= :startDate) AND " +
            "(:endDate IS NULL OR u.createdAt <= :endDate)")
    List<User> findUsersByDateRange(@Param("startDate") Instant startDate, @Param("endDate") Instant endDate);
}
