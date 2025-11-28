package com.temanmu.temanmu.features.profile.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public class User {

	private UUID id;

	private String name;

	private String username;

	private LocalDate dateOfBirth;

	private String email;

	private String phone;

	private String password;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	private boolean verified;

	private String profilePicture;

	private Set<Role> roles;

	private ClientProfile clientProfile;

	private CaregiverProfile caregiverProfile;

	private PeerProfile peerProfile;

	public User(UUID id, String name, String username, LocalDate dateOfBirth, String email, String phone,
			String password, LocalDateTime createdAt, LocalDateTime updatedAt, boolean verified, String profilePicture,
			Set<Role> roles, ClientProfile clientProfile, CaregiverProfile caregiverProfile, PeerProfile peerProfile) {
		this.id = id;
		this.name = name;
		this.username = username;
		this.dateOfBirth = dateOfBirth;
		this.email = email;
		this.phone = phone;
		this.password = password;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.verified = verified;
		this.profilePicture = profilePicture;
		this.roles = roles;
		this.clientProfile = clientProfile;
		this.caregiverProfile = caregiverProfile;
		this.peerProfile = peerProfile;
	}

	public UUID getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getUsername() {
		return username;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public String getPassword() {
		return password;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public boolean isVerified() {
		return verified;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public ClientProfile getClientProfile() {
		return clientProfile;
	}

	public CaregiverProfile getCaregiverProfile() {
		return caregiverProfile;
	}

	public PeerProfile getPeerProfile() {
		return peerProfile;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
