package com.temani.temani.features.profile.infrastructure.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.temani.temani.features.profile.domain.model.User;
import com.temani.temani.features.profile.domain.repository.UserRepository;
import com.temani.temani.features.profile.infrastructure.mapper.UserEntityMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

	private final UserJpaRepository jpa;

	private final UserEntityMapper mapper;

	@Override
	public User save(User user) {
		UserEntity savedEntity = jpa.save(mapper.toEntity(user));
		return mapper.toDomain(savedEntity);
	}

	@Override
	public Optional<User> findById(UUID id) {
		return jpa.findById(id).map(mapper::toDomain);
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

	@Override
	public List<User> findAllByRoleAndKeyword(String role, String keyword, UUID currentUserId) {
		return jpa.findAllByRoleAndKeyword(role, keyword, currentUserId).stream().map(mapper::toDomain).toList();
	}

	@Override
	public boolean existsByUsername(String username) {
		return jpa.existsByUsername(username);
	}

	@Override
	public boolean existsByEmail(String email) {
		return jpa.existsByEmail(email);
	}

	@Override
	public boolean existsByPhone(String phone) {
		return jpa.existsByPhone(phone);
	}

}
