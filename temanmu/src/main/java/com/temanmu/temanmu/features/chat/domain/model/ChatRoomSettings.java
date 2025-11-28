package com.temanmu.temanmu.features.chat.domain.model;

public class ChatRoomSettings {
    private boolean allowInvites;
    private boolean allowFileUploads;
    private boolean allowMessageEditing;
    private boolean allowMessageDeletion;
    private int maxParticipants;
    private int messageRetentionDays;
    private boolean requireApproval;

    public ChatRoomSettings(boolean allowInvites, boolean allowFileUploads,
            boolean allowMessageEditing, boolean allowMessageDeletion,
            int maxParticipants, int messageRetentionDays,
            boolean requireApproval) {
        this.allowInvites = allowInvites;
        this.allowFileUploads = allowFileUploads;
        this.allowMessageEditing = allowMessageEditing;
        this.allowMessageDeletion = allowMessageDeletion;
        this.maxParticipants = maxParticipants;
        this.messageRetentionDays = messageRetentionDays;
        this.requireApproval = requireApproval;
    }

    // Getters
    public boolean isAllowInvites() {
        return allowInvites;
    }

    public boolean isAllowFileUploads() {
        return allowFileUploads;
    }

    public boolean isAllowMessageEditing() {
        return allowMessageEditing;
    }

    public boolean isAllowMessageDeletion() {
        return allowMessageDeletion;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public int getMessageRetentionDays() {
        return messageRetentionDays;
    }

    public boolean isRequireApproval() {
        return requireApproval;
    }
}
