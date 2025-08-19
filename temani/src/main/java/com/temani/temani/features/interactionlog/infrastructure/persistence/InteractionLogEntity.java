package com.temani.temani.features.interactionlog.infrastructure.persistence;

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
@Table(name = "interaction_logs")
public class InteractionLogEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "feature", nullable = false)
    private String feature; // "moodlog", "todo"

    @Column(name = "action", nullable = false)
    private String action; // "create", "update", "delete", "toggle"

    @Column(name = "entity_type", nullable = false)
    private String entityType; // "moodlog", "todolist", "todoitem"

    @Column(name = "entity_id", nullable = false)
    private UUID entityId;

    @Column(name = "title", nullable = false)
    private String title; // Human readable title

    @Column(name = "description", nullable = false)
    private String description; // Human readable description

    @CreationTimestamp
    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

}