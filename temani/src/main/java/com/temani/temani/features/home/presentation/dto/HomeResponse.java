package com.temani.temani.features.home.presentation.dto;

import java.util.List;

import com.temani.temani.features.moodlog.presentation.dto.response.MoodLogResponse;
import com.temani.temani.features.todo.presentation.dto.response.ToDoItemResponse;

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

    private List<Object> counselingSchedules;

}
