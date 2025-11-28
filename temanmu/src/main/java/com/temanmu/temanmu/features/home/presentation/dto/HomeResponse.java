package com.temanmu.temanmu.features.home.presentation.dto;

import java.util.List;

import com.temanmu.temanmu.features.counseling.presentation.dto.CounselingScheduleResponse;
import com.temanmu.temanmu.features.moodlog.presentation.dto.response.MoodLogResponse;
import com.temanmu.temanmu.features.todo.presentation.dto.response.ToDoItemResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomeResponse {

    private List<MoodLogResponse> todayMoodLogs;

    private List<ToDoItemResponse> todayToDoItems;

    private long todayTotalToDos;

    private List<CounselingScheduleResponse> counselingSchedules;

}
