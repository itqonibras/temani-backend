package com.temanmu.temanmu.features.relationship.infrastructure.mapper;

import org.mapstruct.Mapper;

import com.temanmu.temanmu.features.relationship.domain.model.Relationship;
import com.temanmu.temanmu.features.relationship.presentation.dto.response.RelationshipResponse;

@Mapper(componentModel = "spring")
public interface RelationshipDtoMapper {

	RelationshipResponse toDto(Relationship domain);

}
