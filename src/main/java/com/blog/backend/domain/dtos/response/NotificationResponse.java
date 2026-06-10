package com.blog.backend.domain.dtos.response;

import com.blog.backend.domain.NotificationType;

import java.time.LocalDateTime;
import java.util.UUID;

public class NotificationResponse {
    private UUID senderId;
    private UUID notificationId;
    private String senderName;
    private String message;
    private boolean read;
    private LocalDateTime lastUpdate;
    private NotificationType notificationType;

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public UUID getSenderId() {
        return senderId;
    }

    public void setSenderId(UUID senderId) {
        this.senderId = senderId;
    }

    public UUID getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(UUID notificationId) {
        this.notificationId = notificationId;
    }

    public NotificationResponse(UUID senderId, UUID notificationId, String senderName, String message, boolean read, LocalDateTime lastUpdate, NotificationType notificationType) {
        this.senderId = senderId;
        this.notificationId = notificationId;
        this.senderName = senderName;
        this.message = message;
        this.read = read;
        this.lastUpdate = lastUpdate;
        this.notificationType = notificationType;
    }
}
