package com.temani.temani.features.moodlog.common;

public enum EmotionScale {
	
	SANGAT_BURUK(1, "Sangat Buruk"),
	BURUK(2, "Buruk"),
	BIASA_SAJA(3, "Biasa Saja"),
	BAIK(4, "Baik"),
	SANGAT_BAIK(5, "Sangat Baik");
	
	private final int value;
	private final String description;
	
	EmotionScale(int value, String description) {
		this.value = value;
		this.description = description;
	}
	
	public int getValue() {
		return value;
	}
	
	public String getDescription() {
		return description;
	}
	
	public static EmotionScale fromValue(int value) {
		for (EmotionScale scale : values()) {
			if (scale.value == value) {
				return scale;
			}
		}
		throw new IllegalArgumentException("Invalid emotion scale value: " + value);
	}
	
	public static boolean isValid(int value) {
		return value >= 1 && value <= 5;
	}
	
} 