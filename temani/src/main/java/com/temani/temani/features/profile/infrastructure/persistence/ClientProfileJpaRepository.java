package com.temani.temani.features.profile.infrastructure.persistence;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientProfileJpaRepository extends JpaRepository<ClientProfileEntity, UUID> {

}
