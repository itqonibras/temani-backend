package com.temani.temani.features.relationship.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Relationship {

	private UUID id;

	private UUID clientId;

	private String clientName;

	private UUID caregiverId;

	private String caregiverName;

	private UUID initiatorId;

	private boolean accepted;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	public Relationship(UUID id, UUID clientId, String clientName, UUID caregiverId, String caregiverName,
			UUID initiatorId, boolean accepted, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.id = id;
		this.clientId = clientId;
		this.clientName = clientName;
		this.caregiverId = caregiverId;
		this.caregiverName = caregiverName;
		this.initiatorId = initiatorId;
		this.accepted = accepted;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public UUID getId() {
		return id;
	}

	public UUID getClientId() {
		return clientId;
	}

	public String getClientName() {
		return clientName;
	}

	public UUID getCaregiverId() {
		return caregiverId;
	}

	public String getCaregiverName() {
		return caregiverName;
	}

	public UUID getInitiatorId() {
		return initiatorId;
	}

	public boolean isAccepted() {
		return accepted;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

}
