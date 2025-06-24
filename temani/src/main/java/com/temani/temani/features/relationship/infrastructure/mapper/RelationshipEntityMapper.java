package com.temani.temani.features.relationship.infrastructure.mapper;

import org.mapstruct.Mapper;

import com.temani.temani.features.relationship.domain.model.Relationship;
import com.temani.temani.features.relationship.infrastructure.persistence.RelationshipEntity;

@Mapper(componentModel = "spring")
public interface RelationshipEntityMapper {
    RelationshipEntity toEntity(Relationship domain);
    Relationship toDomain(RelationshipEntity entity);
}
