package com.temani.temani.features.profile.domain.model;

import java.util.UUID;

public class PeerProfile {

	private UUID id;

	public PeerProfile(UUID id) {
		this.id = id;
	}

	public UUID getId() {
		return id;
	}

}
