package com.temani.temani.features.profile.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.temani.temani.features.profile.domain.model.User;

public interface UserRepository {

    User save(User user);

    Optional<User> findById(UUID id);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailOrUsername(String emailOrUsername);

    List<User> findAllByRoleAndKeyword(String role, String keyword, UUID currentUserId);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);
    
}
