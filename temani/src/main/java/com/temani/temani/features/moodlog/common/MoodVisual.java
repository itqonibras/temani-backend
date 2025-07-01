package com.temani.temani.features.moodlog.common;

public enum MoodVisual {
    SANGAT_BURUK("sangat buruk"),
    BURUK("buruk"),
    BIASA_SAJA("biasa saja"),
    BAIK("baik"),
    SANGAT_BAIK("sangat baik");

    private final String value;

    MoodVisual(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static boolean isValid(String value) {
        for (MoodVisual mv : values()) {
            if (mv.value.equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
} 