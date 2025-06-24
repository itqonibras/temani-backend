package com.temani.temani.features.profile.domain.model;

import java.util.UUID;

public class CaregiverProfile {

	private UUID id;

	public CaregiverProfile(UUID id) {
		this.id = id;
	}

	public UUID getId() {
		return id;
	}

}
