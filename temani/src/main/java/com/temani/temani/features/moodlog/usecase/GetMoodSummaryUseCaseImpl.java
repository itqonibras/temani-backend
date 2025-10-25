package com.temani.temani.features.moodlog.usecase;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.temani.temani.features.moodlog.domain.model.MoodLog;
import com.temani.temani.features.moodlog.domain.repository.MoodLogRepository;
import com.temani.temani.features.moodlog.presentation.dto.response.MoodSummaryResponse;
import com.temani.temani.features.moodlog.presentation.dto.response.MoodSummaryResponse.BestMood;
import com.temani.temani.features.moodlog.presentation.dto.response.MoodSummaryResponse.WeeklyMood;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetMoodSummaryUseCaseImpl implements GetMoodSummaryUseCase {

    private final MoodLogRepository moodLogRepository;

    @Override
    public MoodSummaryResponse execute(UUID userId, LocalDate weekStart) {
        // Calculate week end (6 days after start)
        LocalDate weekEnd = weekStart.plusDays(6);

        // Get mood logs for the specified week
        LocalDateTime startDateTime = weekStart.atStartOfDay();
        LocalDateTime endDateTime = weekEnd.atTime(23, 59, 59);

        List<MoodLog> moodLogs = moodLogRepository.findAllByUserIdAndTimestampBetween(userId, startDateTime,
                endDateTime);

        // Calculate mood average
        double averageScore = calculateAverageScore(moodLogs);
        String moodAverage = getMoodVisualFromScore(averageScore);

        // Find best mood
        BestMood bestMood = findBestMood(moodLogs);

        // Create weekly mood data
        WeeklyMood weeklyMood = createWeeklyMood(moodLogs, weekStart);

        return new MoodSummaryResponse(
                moodAverage,
                averageScore,
                bestMood,
                weeklyMood,
                weekStart,
                weekEnd);
    }

    private double calculateAverageScore(List<MoodLog> moodLogs) {
        if (moodLogs.isEmpty()) {
            return 0.0;
        }

        double sum = moodLogs.stream()
                .mapToDouble(MoodLog::getEmotionScale)
                .sum();

        return sum / moodLogs.size();
    }

    private String getMoodVisualFromScore(double score) {
        int roundedScore = (int) Math.round(score);

        switch (roundedScore) {
            case 1:
                return "sangat buruk";
            case 2:
                return "buruk";
            case 3:
                return "biasa saja";
            case 4:
                return "baik";
            case 5:
                return "sangat baik";
            default:
                return "biasa saja";
        }
    }

    private BestMood findBestMood(List<MoodLog> moodLogs) {
        if (moodLogs.isEmpty()) {
            return null;
        }

        MoodLog bestMoodLog = moodLogs.stream()
                .max((m1, m2) -> Integer.compare(m1.getEmotionScale(), m2.getEmotionScale()))
                .orElse(null);

        if (bestMoodLog == null) {
            return null;
        }

        LocalDate moodDate = bestMoodLog.getTimestamp().toLocalDate();
        String dayOfWeek = moodDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);

        return new BestMood(
                bestMoodLog.getMoodVisual(),
                bestMoodLog.getEmotionScale(),
                moodDate,
                dayOfWeek);
    }

    private WeeklyMood createWeeklyMood(List<MoodLog> moodLogs, LocalDate weekStart) {
        // Create a map of day -> mood for the week
        Map<DayOfWeek, String> weeklyMoodMap = new HashMap<>();
        Map<String, Integer> moodCounts = new HashMap<>();

        // Initialize all days with "No mood logged"
        for (int i = 0; i < 7; i++) {
            DayOfWeek dayOfWeek = weekStart.plusDays(i).getDayOfWeek();
            weeklyMoodMap.put(dayOfWeek, "No mood logged");
        }

        // Fill in actual mood data
        for (MoodLog moodLog : moodLogs) {
            LocalDate moodDate = moodLog.getTimestamp().toLocalDate();
            DayOfWeek dayOfWeek = moodDate.getDayOfWeek();

            // Only include moods from the specified week
            if (!moodDate.isBefore(weekStart) && !moodDate.isAfter(weekStart.plusDays(6))) {
                weeklyMoodMap.put(dayOfWeek, moodLog.getMoodVisual());

                // Count mood occurrences
                moodCounts.merge(moodLog.getMoodVisual(), 1, Integer::sum);
            }
        }

        return new WeeklyMood(
                weeklyMoodMap.get(DayOfWeek.MONDAY),
                weeklyMoodMap.get(DayOfWeek.TUESDAY),
                weeklyMoodMap.get(DayOfWeek.WEDNESDAY),
                weeklyMoodMap.get(DayOfWeek.THURSDAY),
                weeklyMoodMap.get(DayOfWeek.FRIDAY),
                weeklyMoodMap.get(DayOfWeek.SATURDAY),
                weeklyMoodMap.get(DayOfWeek.SUNDAY),
                moodCounts);
    }

}
