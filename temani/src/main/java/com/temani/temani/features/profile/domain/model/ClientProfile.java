package com.temani.temani.features.profile.domain.model;

import java.util.UUID;

public class ClientProfile {

	private UUID id;

	private String condition;

	private String aboutMe;

	public ClientProfile(UUID id, String condition, String aboutMe) {
		this.id = id;
		this.condition = condition;
		this.aboutMe = aboutMe;
	}

	public UUID getId() {
		return id;
	}

	public String getCondition() {
		return condition;
	}

	public String getAboutMe() {
		return aboutMe;
	}

}
