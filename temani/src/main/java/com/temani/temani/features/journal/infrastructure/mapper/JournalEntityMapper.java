package com.temani.temani.features.journal.infrastructure.mapper;

import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.temani.temani.features.journal.domain.model.Journal;
import com.temani.temani.features.journal.infrastructure.persistance.JournalEntity;
import com.temani.temani.features.profile.infrastructure.persistence.UserEntity;

@Mapper(componentModel = "spring")
public interface JournalEntityMapper {

    @Mapping(target = "user", source = "userId", qualifiedByName = "mapUserIdToUserEntity")
    JournalEntity toEntity(Journal domain);

    @Named("mapUserIdToUserEntity")
    static UserEntity mapUserIdToUserEntity(UUID userId) {
        if (userId == null) return null;
        var user = new UserEntity();
        user.setId(userId);
        return user;
    }

    @Mapping(source = "user.id", target = "userId")
    Journal toDomain(JournalEntity entity);
}
