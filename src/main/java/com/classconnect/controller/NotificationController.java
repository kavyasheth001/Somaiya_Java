package com.classconnect.controller;

import com.classconnect.dto.NotificationResponse;
import com.classconnect.entity.Notification;
import com.classconnect.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*")
public class NotificationController {
    
    @Autowired
    private NotificationService notificationService;
    
    @GetMapping
    public ResponseEntity<?> getNotifications(
            @RequestParam UUID userId,
            @RequestParam(required = false) Notification.NotificationType type,
            @RequestParam(required = false) Boolean unreadOnly) {
        try {
            if (unreadOnly != null && unreadOnly) {
                var notifications = notificationService.getUnreadNotificationsByUser(userId);
                return ResponseEntity.ok(notifications);
            } else if (type != null) {
                var notifications = notificationService.getNotificationsByType(userId, type);
                return ResponseEntity.ok(notifications);
            } else {
                var notifications = notificationService.getNotificationsByUser(userId);
                return ResponseEntity.ok(notifications);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", e.getMessage()
            ));
        }
    }
    
    @GetMapping("/count/{userId}")
    public ResponseEntity<?> getUnreadNotificationCount(@PathVariable UUID userId) {
        try {
            Long count = notificationService.getUnreadNotificationCount(userId);
            return ResponseEntity.ok(Map.of(
                "userId", userId,
                "unreadCount", count
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", e.getMessage()
            ));
        }
    }
    
    @PutMapping("/{id}/read")
    public ResponseEntity<?> markAsRead(@PathVariable UUID id) {
        try {
            NotificationResponse notification = notificationService.markAsRead(id);
            return ResponseEntity.ok(Map.of(
                "message", "Notification marked as read",
                "notification", notification
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", e.getMessage()
            ));
        }
    }
    
    @PutMapping("/mark-all-read/{userId}")
    public ResponseEntity<?> markAllAsRead(@PathVariable UUID userId) {
        try {
            notificationService.markAllAsRead(userId);
            return ResponseEntity.ok(Map.of(
                "message", "All notifications marked as read"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", e.getMessage()
            ));
        }
    }
    
    @GetMapping("/since/{userId}")
    public ResponseEntity<?> getNotificationsSince(
            @PathVariable UUID userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime since) {
        try {
            var notifications = notificationService.getNotificationsSince(userId, since);
            return ResponseEntity.ok(notifications);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", e.getMessage()
            ));
        }
    }
}

