package com.temani.temani.features.profile.domain.repository;

import java.util.Optional;

import com.temani.temani.features.profile.domain.model.User;

public interface UserRepository {
    void save(User user);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
