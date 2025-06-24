package com.temani.temani.features.profile.infrastructure.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {

	Optional<UserEntity> findById(UUID id);

	Optional<UserEntity> findByUsername(String username);

	Optional<UserEntity> findByEmail(String email);

	@Query("""
			    SELECT u FROM UserEntity u
			    JOIN u.roles r
			    WHERE LOWER(r.name) = LOWER(:role)
			      AND u.id <> :excludeId
			      AND (
			        LOWER(u.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
			        OR LOWER(u.username) LIKE LOWER(CONCAT('%', :keyword, '%'))
			      )
			""")
	List<UserEntity> findAllByRoleAndKeyword(@Param("role") String role, @Param("keyword") String keyword,
			@Param("excludeId") UUID excludeId);

	boolean existsByUsername(String username);

	boolean existsByEmail(String email);

	boolean existsByPhone(String phone);

}
