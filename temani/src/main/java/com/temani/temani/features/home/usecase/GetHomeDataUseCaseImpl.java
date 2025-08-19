package com.temani.temani.features.home.usecase;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.temani.temani.features.home.presentation.dto.HomeResponse;
import com.temani.temani.features.moodlog.domain.repository.MoodLogRepository;
import com.temani.temani.features.moodlog.infrastructure.mapper.MoodLogDtoMapper;
import com.temani.temani.features.todo.domain.repository.ToDoItemRepository;
import com.temani.temani.features.todo.infrastructure.mapper.ToDoItemDtoMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetHomeDataUseCaseImpl implements GetHomeDataUseCase {

    private final MoodLogRepository moodLogRepository;
    private final MoodLogDtoMapper moodLogDtoMapper;
    private final ToDoItemRepository toDoItemRepository;
    private final ToDoItemDtoMapper toDoItemDtoMapper;

    @Override
    public HomeResponse execute(UUID userId) {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

        var moodLogsList = moodLogRepository
                .findAllByUserIdAndTimestampBetween(userId, startOfDay, endOfDay)
                .stream().map(moodLogDtoMapper::toDto).toList();

        var toDoItems = toDoItemRepository
                .findAllByUserIdAndCreatedAtBetween(userId, startOfDay, endOfDay)
                .stream().map(toDoItemDtoMapper::toDto)
                .limit(3)
                .toList();

        long totalTodosToday = toDoItemRepository.countByUserIdAndCreatedAtBetween(userId, startOfDay, endOfDay);

        // Counseling schedules: no model yet → return empty list for now
        List<Object> counselingSchedules = Collections.emptyList();

        return new HomeResponse(moodLogsList.isEmpty() ? null : moodLogsList, toDoItems, totalTodosToday,
                counselingSchedules);
    }
}
