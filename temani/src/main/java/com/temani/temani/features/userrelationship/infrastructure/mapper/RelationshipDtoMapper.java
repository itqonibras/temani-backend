package com.temani.temani.features.userrelationship.infrastructure.mapper;

import org.mapstruct.Mapper;

import com.temani.temani.features.userrelationship.domain.model.Relationship;
import com.temani.temani.features.userrelationship.presentation.dto.response.RelationshipResponse;

@Mapper(componentModel = "spring")
public interface RelationshipDtoMapper {
    RelationshipResponse toDto(Relationship domain);
}
