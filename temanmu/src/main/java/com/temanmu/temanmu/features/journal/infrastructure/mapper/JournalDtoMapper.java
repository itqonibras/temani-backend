package com.temanmu.temanmu.features.journal.infrastructure.mapper;

import org.mapstruct.Mapper;

import com.temanmu.temanmu.features.journal.domain.model.Journal;
import com.temanmu.temanmu.features.journal.presentation.dto.response.JournalResponse;

@Mapper(componentModel = "spring")
public interface JournalDtoMapper {

	JournalResponse toDto(Journal journal);

}
