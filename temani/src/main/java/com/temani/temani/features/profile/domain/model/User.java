package com.temani.temani.features.profile.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public class User {
    private final UUID id;
    private final String name;
    private final String username;
    private final LocalDate dateOfBirth;
    private final String email;
    private final String phone;
    private final String password;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final boolean verified;
    private final Set<Role> roles;

    public User(
            UUID id,
            String name,
            String username,
            LocalDate dateOfBirth,
            String email,
            String phone,
            String password,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            boolean verified,
            Set<Role> roles) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.verified = verified;
        this.roles = roles;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public boolean isVerified() {
        return verified;
    }

    public Set<Role> getRoles() {
        return roles;
    }
}
