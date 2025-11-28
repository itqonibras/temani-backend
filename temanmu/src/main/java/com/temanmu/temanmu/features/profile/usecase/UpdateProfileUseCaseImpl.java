package com.temanmu.temanmu.features.profile.usecase;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.temanmu.temanmu.common.constants.CommonMessages;
import com.temanmu.temanmu.common.constants.ProfileMessages;
import com.temanmu.temanmu.features.profile.domain.model.User;
import com.temanmu.temanmu.features.profile.domain.repository.UserRepository;
import com.temanmu.temanmu.features.profile.infrastructure.mapper.UserDtoMapper;
import com.temanmu.temanmu.features.profile.infrastructure.storage.SupabaseStorageService;
import com.temanmu.temanmu.features.profile.presentation.dto.request.UpdateProfileRequest;
import com.temanmu.temanmu.features.profile.presentation.dto.response.UserResponse;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateProfileUseCaseImpl implements UpdateProfileUseCase {

	private final UserRepository userRepository;
	private final UserDtoMapper userDtoMapper;
	private final SupabaseStorageService storageService;

	@Transactional
	@Override
	public UserResponse execute(UUID userId, UpdateProfileRequest request, MultipartFile profilePicture) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException(CommonMessages.USER_NOT_FOUND));

		// Update basic profile fields only if provided
		if (request != null) {
			if (request.getName() != null && !request.getName().trim().isEmpty()) {
				user.setName(request.getName());
			}
			if (request.getUsername() != null && !request.getUsername().trim().isEmpty()) {
				user.setUsername(request.getUsername());
			}
			if (request.getDateOfBirth() != null) {
				user.setDateOfBirth(request.getDateOfBirth());
			}
			if (request.getEmail() != null && !request.getEmail().trim().isEmpty()) {
				user.setEmail(request.getEmail());
			}
			if (request.getPhone() != null && !request.getPhone().trim().isEmpty()) {
				user.setPhone(request.getPhone());
			}
		}

		// Handle profile picture upload if provided
		if (profilePicture != null && !profilePicture.isEmpty()) {
			try {
				// Delete old profile picture if exists
				if (user.getProfilePicture() != null && !user.getProfilePicture().isEmpty()) {
					try {
						storageService.deleteFile(user.getProfilePicture());
					}
					catch (Exception e) {
						log.warn("Failed to delete old profile picture: {}", e.getMessage());
					}
				}

				// Upload new profile picture
				String fileName = profilePicture.getOriginalFilename();
				if (fileName == null || fileName.isEmpty()) {
					fileName = "profile.jpg";
				}

				String profilePictureUrl = storageService.uploadFile(
						profilePicture.getBytes(),
						fileName,
						profilePicture.getContentType());

				user.setProfilePicture(profilePictureUrl);
			}
			catch (Exception e) {
				log.error("Error uploading profile picture: {}", e.getMessage(), e);
				throw new RuntimeException(ProfileMessages.PROFILE_PICTURE_UPLOAD_FAILED + ": " + e.getMessage());
			}
		}

		User savedUser = userRepository.save(user);
		return userDtoMapper.toDto(savedUser);
	}

}

