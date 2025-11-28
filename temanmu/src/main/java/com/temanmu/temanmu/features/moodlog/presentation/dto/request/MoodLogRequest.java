package com.temanmu.temanmu.features.moodlog.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

import com.temanmu.temanmu.features.moodlog.common.MoodVisual;

import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoodLogRequest {

	@NotBlank(message = "Mood visual can't be empty!")
	private String moodVisual;

	@NotNull(message = "Emotion scale can't be null!")
	@Min(value = 1, message = "Emotion scale must be between 1 and 5")
	@Max(value = 5, message = "Emotion scale must be between 1 and 5")
	private Integer emotionScale;

	public boolean isMoodVisualValid() {
		return MoodVisual.isValid(moodVisual);
	}
}