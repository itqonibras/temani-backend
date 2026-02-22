package com.temanmu.temanmu.features.relationship.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Relationship {

	private UUID id;

	private UUID clientId;

	private String clientName;

	private String clientProfilePicture;

	private UUID caregiverId;

	private String caregiverName;

	private String caregiverProfilePicture;

	private UUID initiatorId;

	private boolean accepted;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	public Relationship(UUID id, UUID clientId, String clientName, String clientProfilePicture,
			UUID caregiverId, String caregiverName, String caregiverProfilePicture,
			UUID initiatorId, boolean accepted, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.id = id;
		this.clientId = clientId;
		this.clientName = clientName;
		this.clientProfilePicture = clientProfilePicture;
		this.caregiverId = caregiverId;
		this.caregiverName = caregiverName;
		this.caregiverProfilePicture = caregiverProfilePicture;
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

	public String getClientProfilePicture() {
		return clientProfilePicture;
	}

	public UUID getCaregiverId() {
		return caregiverId;
	}

	public String getCaregiverName() {
		return caregiverName;
	}

	public String getCaregiverProfilePicture() {
		return caregiverProfilePicture;
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
