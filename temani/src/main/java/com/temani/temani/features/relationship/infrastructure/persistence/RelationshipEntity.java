package com.temani.temani.features.relationship.infrastructure.persistence;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_relationship")
public class RelationshipEntity {

	@Id
	@GeneratedValue(generator = "UUID")
	private UUID id;

	@Column(name = "client_id", nullable = false)
	private UUID clientId;

	@Column(name = "caregiver_id", nullable = false, unique = true)
	private UUID caregiverId;

	@Column(name = "initiator_id", nullable = false)
	private UUID initiatorId;

	@NotNull
	@Column(name = "accepted", nullable = false)
	private boolean accepted;

	@CreationTimestamp
	@Column(name = "created_at", updatable = false, nullable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;

}
