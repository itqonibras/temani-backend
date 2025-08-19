package com.temani.temani.features.interactionlog.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class InteractionLog {

    private UUID id;
    private UUID userId;
    private String feature; // "moodlog", "todo"
    private String action; // "create", "update", "delete", "toggle"
    private String entityType; // "moodlog", "todolist", "todoitem"
    private UUID entityId;
    private String title; // Human readable title
    private String description; // Human readable description
    private LocalDateTime timestamp;

    public InteractionLog(UUID id, UUID userId, String feature, String action, String entityType, UUID entityId,
            String title, String description, LocalDateTime timestamp) {
        this.id = id;
        this.userId = userId;
        this.feature = feature;
        this.action = action;
        this.entityType = entityType;
        this.entityId = entityId;
        this.title = title;
        this.description = description;
        this.timestamp = timestamp;
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getFeature() {
        return feature;
    }

    public String getAction() {
        return action;
    }

    public String getEntityType() {
        return entityType;
    }

    public UUID getEntityId() {
        return entityId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}