package com.temani.temani.features.profile.presentation.dto.response;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientProfileResponse {
    private UUID id;
    private String condition;
    private String aboutMe;
}
