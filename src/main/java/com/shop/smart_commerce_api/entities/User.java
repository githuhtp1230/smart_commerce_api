package com.shop.smart_commerce_api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@Entity
@Table(name = "users", schema = "smart_commerce")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "avatar", length = 500)
    private String avatar;

    @Column(name = "phone", length = 10)
    private String phone;

    @Column(name = "google_id")
    private Integer googleId;

    @Column(name = "is_active")
    private Boolean isActive;

    @OneToMany(mappedBy = "user")
    private Set<Address> addresses = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<CartDetail> cartDetails = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Order> orders = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Review> reviews = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<UsersRole> usersRoles = new LinkedHashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();

        for (UsersRole usersRole : this.usersRoles) {
            for (RolePermission rolePermission : usersRole.getRole().getRolePermissions()) {
                authorities.add(new SimpleGrantedAuthority(rolePermission.getPermission().getName()));
            }
        }

        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

}