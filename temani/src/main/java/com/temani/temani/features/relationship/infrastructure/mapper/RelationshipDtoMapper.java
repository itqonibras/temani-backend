package com.temani.temani.features.relationship.infrastructure.mapper;

import org.mapstruct.Mapper;

import com.temani.temani.features.relationship.domain.model.Relationship;
import com.temani.temani.features.relationship.presentation.dto.response.RelationshipResponse;

@Mapper(componentModel = "spring")
public interface RelationshipDtoMapper {
    RelationshipResponse toDto(Relationship domain);
}
