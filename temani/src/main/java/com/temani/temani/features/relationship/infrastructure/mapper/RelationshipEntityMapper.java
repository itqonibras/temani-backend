package com.temani.temani.features.relationship.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.temani.temani.features.relationship.domain.model.Relationship;
import com.temani.temani.features.relationship.infrastructure.persistence.RelationshipEntity;

@Mapper(componentModel = "spring")
public interface RelationshipEntityMapper {

	@Mapping(target = "clientName", ignore = true)
	@Mapping(target = "caregiverName", ignore = true)
	Relationship toDomain(RelationshipEntity entity);

	RelationshipEntity toEntity(Relationship domain);

}
