package com.temani.temani.features.moodlog.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.temani.temani.features.moodlog.common.MoodVisual;

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