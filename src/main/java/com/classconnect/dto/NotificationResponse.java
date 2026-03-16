package com.classconnect.dto;

import com.classconnect.entity.Notification;
import java.time.LocalDateTime;
import java.util.UUID;

public class NotificationResponse {
    
    private UUID id;
    private String message;
    private Notification.NotificationType type;
    private UserResponse recipient;
    private Boolean isRead;
    private LocalDateTime createdAt;
    
    public NotificationResponse() {}
    
    public NotificationResponse(Notification notification) {
        this.id = notification.getId();
        this.message = notification.getMessage();
        this.type = notification.getType();
        this.recipient = new UserResponse(notification.getRecipient());
        this.isRead = notification.getIsRead();
        this.createdAt = notification.getCreatedAt();
    }
    
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public Notification.NotificationType getType() { return type; }
    public void setType(Notification.NotificationType type) { this.type = type; }
    
    public UserResponse getRecipient() { return recipient; }
    public void setRecipient(UserResponse recipient) { this.recipient = recipient; }
    
    public Boolean getIsRead() { return isRead; }
    public void setIsRead(Boolean isRead) { this.isRead = isRead; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

