package com.temani.temani.features.profile.domain.repository;

import java.util.Optional;

import com.temani.temani.features.profile.domain.model.User;

public interface UserRepository {
    User save(User user);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailOrUsername(String emailOrUsername);
    
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
}
