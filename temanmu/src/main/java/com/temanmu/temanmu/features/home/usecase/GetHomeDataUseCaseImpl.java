package com.temanmu.temanmu.features.home.usecase;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.temanmu.temanmu.features.counseling.domain.repository.CounselingScheduleRepository;
import com.temanmu.temanmu.features.counseling.infrastructure.mapper.CounselingScheduleDtoMapper;
import com.temanmu.temanmu.features.home.presentation.dto.HomeResponse;
import com.temanmu.temanmu.features.moodlog.domain.repository.MoodLogRepository;
import com.temanmu.temanmu.features.moodlog.infrastructure.mapper.MoodLogDtoMapper;
import com.temanmu.temanmu.features.todo.domain.repository.ToDoItemRepository;
import com.temanmu.temanmu.features.todo.infrastructure.mapper.ToDoItemDtoMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetHomeDataUseCaseImpl implements GetHomeDataUseCase {

        private final MoodLogRepository moodLogRepository;
        private final MoodLogDtoMapper moodLogDtoMapper;
        private final ToDoItemRepository toDoItemRepository;
        private final ToDoItemDtoMapper toDoItemDtoMapper;
        private final CounselingScheduleRepository counselingScheduleRepository;
        private final CounselingScheduleDtoMapper counselingScheduleDtoMapper;

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

                long totalTodosToday = toDoItemRepository.countByUserIdAndCreatedAtBetween(userId, startOfDay,
                                endOfDay);

                var clientSchedules = counselingScheduleRepository.findAllByClientId(userId);
                var caregiverSchedules = counselingScheduleRepository.findAllByCounselorId(userId);
                var schedules = (!clientSchedules.isEmpty() ? clientSchedules : caregiverSchedules)
                                .stream().map(counselingScheduleDtoMapper::toDto).toList();

                return new HomeResponse(moodLogsList.isEmpty() ? null : moodLogsList, toDoItems, totalTodosToday,
                                schedules);
        }
}
