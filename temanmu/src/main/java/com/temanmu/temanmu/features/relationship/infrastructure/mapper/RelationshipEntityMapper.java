package com.temanmu.temanmu.features.relationship.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.temanmu.temanmu.features.relationship.domain.model.Relationship;
import com.temanmu.temanmu.features.relationship.infrastructure.persistence.RelationshipEntity;

@Mapper(componentModel = "spring")
public interface RelationshipEntityMapper {

	@Mapping(target = "clientName", ignore = true)
	@Mapping(target = "clientProfilePicture", ignore = true)
	@Mapping(target = "caregiverName", ignore = true)
	@Mapping(target = "caregiverProfilePicture", ignore = true)
	Relationship toDomain(RelationshipEntity entity);

	RelationshipEntity toEntity(Relationship domain);

}
