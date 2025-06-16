package com.temani.temani.features.profile.infrastructure.persistence;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.temani.temani.features.profile.domain.model.User;
import com.temani.temani.features.profile.domain.repository.UserRepository;
import com.temani.temani.features.profile.infrastructure.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository jpa;
    private final UserMapper mapper;
    
    @Override
    public void save(User user) {
        jpa.save(mapper.toEntity(user));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return jpa.findByUsername(username).map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpa.findByEmail(email).map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByEmailOrUsername(String emailOrUsername) {
        Optional<User> user = jpa.findByEmail(emailOrUsername).map(mapper::toDomain);
        if (user.isEmpty()) {
            user = jpa.findByUsername(emailOrUsername).map(mapper::toDomain);
        }
        return user;
    }
}
