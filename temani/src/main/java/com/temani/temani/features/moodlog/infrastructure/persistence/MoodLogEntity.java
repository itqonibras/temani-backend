package com.temani.temani.features.moodlog.infrastructure.persistence;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.temani.temani.features.profile.infrastructure.persistence.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mood_logs")
public class MoodLogEntity {

	@Id
	@GeneratedValue(generator = "UUID")
	private UUID id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id")
	private UserEntity user;

	@Column(name = "mood_visual", nullable = false)
	private String moodVisual;

	@Column(name = "emotion_scale", nullable = false)
	private Integer emotionScale; // 1: sangat buruk, 2: buruk, 3: biasa saja, 4: baik, 5: sangat baik

	@CreationTimestamp
	@Column(name = "timestamp", nullable = false)
	private LocalDateTime timestamp;

} 