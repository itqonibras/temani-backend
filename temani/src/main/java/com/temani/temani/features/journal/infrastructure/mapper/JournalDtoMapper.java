package com.temani.temani.features.journal.infrastructure.mapper;

import org.mapstruct.Mapper;

import com.temani.temani.features.journal.domain.model.Journal;
import com.temani.temani.features.journal.presentation.dto.response.JournalResponse;

@Mapper(componentModel = "spring")
public interface JournalDtoMapper {

	JournalResponse toDto(Journal journal);

}
