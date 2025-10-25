package com.temani.temani.features.moodlog.presentation.dto.response;

import java.time.LocalDate;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoodSummaryResponse {

    private String moodAverage;
    private Double averageScore;
    private BestMood bestMood;
    private WeeklyMood weeklyMood;
    private LocalDate weekStart;
    private LocalDate weekEnd;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BestMood {
        private String moodVisual;
        private Integer emotionScale;
        private LocalDate date;
        private String dayOfWeek;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WeeklyMood {
        private String monday;
        private String tuesday;
        private String wednesday;
        private String thursday;
        private String friday;
        private String saturday;
        private String sunday;
        private Map<String, Integer> moodCounts; // moodVisual -> count
    }

}
