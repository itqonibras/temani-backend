package com.temani.temani.features.profile.domain.model;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

public class User {
    private final UUID id;
    private final String name;
    private final String username;
    private final String email;
    private final String phone;
    private final String password;
    private final Date createdAt;
    private final Date updatedAt;
    private final boolean isVerified;
    private final Set<Role> roles;

    public User(UUID id, String name, String username, String email, String phone,
            String password, Date createdAt, Date updatedAt, boolean isVerified, Set<Role> roles) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.roles = roles;
        this.isVerified = isVerified;
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

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public Set<Role> getRoles() {
        return roles;
    }
}
