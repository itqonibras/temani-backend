package com.temanmu.temanmu.features.counseling.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.temanmu.temanmu.features.counseling.domain.model.ChatMessage;
import com.temanmu.temanmu.features.counseling.infrastructure.persistence.ChatMessageEntity;

@Mapper(componentModel = "spring")
public interface ChatMessageEntityMapper {
    ChatMessage toDomain(ChatMessageEntity entity);

    @Mapping(target = "id", ignore = true)
    ChatMessageEntity toEntity(ChatMessage domain);
}