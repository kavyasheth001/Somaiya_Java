package com.classconnect.service;

import com.classconnect.dto.NotificationResponse;
import com.classconnect.entity.Notification;
import com.classconnect.entity.User;
import com.classconnect.repository.NotificationRepository;
import com.classconnect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    
    @Autowired
    private NotificationRepository notificationRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public List<NotificationResponse> getNotificationsByUser(UUID userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        
        return notificationRepository.findByRecipientOrderByCreatedAtDesc(userOptional.get()).stream()
                .map(NotificationResponse::new)
                .collect(Collectors.toList());
    }
    
    public List<NotificationResponse> getUnreadNotificationsByUser(UUID userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        
        return notificationRepository.findUnreadByRecipient(userOptional.get()).stream()
                .map(NotificationResponse::new)
                .collect(Collectors.toList());
    }
    
    public Long getUnreadNotificationCount(UUID userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        
        return notificationRepository.countUnreadByRecipient(userOptional.get());
    }
    
    public NotificationResponse markAsRead(UUID notificationId) {
        Optional<Notification> notificationOptional = notificationRepository.findById(notificationId);
        if (notificationOptional.isEmpty()) {
            throw new RuntimeException("Notification not found");
        }
        
        Notification notification = notificationOptional.get();
        notification.setIsRead(true);
        
        return new NotificationResponse(notificationRepository.save(notification));
    }
    
    public List<NotificationResponse> getNotificationsByType(UUID userId, Notification.NotificationType type) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        
        return notificationRepository.findByRecipientAndType(userOptional.get(), type).stream()
                .map(NotificationResponse::new)
                .collect(Collectors.toList());
    }
    
    public List<NotificationResponse> getNotificationsSince(UUID userId, LocalDateTime since) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        
        return notificationRepository.findByRecipientAndCreatedAtAfter(userOptional.get(), since).stream()
                .map(NotificationResponse::new)
                .collect(Collectors.toList());
    }
    
    public void markAllAsRead(UUID userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        
        List<Notification> unreadNotifications = notificationRepository
                .findByRecipientAndIsRead(userOptional.get(), false);
        
        unreadNotifications.forEach(notification -> notification.setIsRead(true));
        notificationRepository.saveAll(unreadNotifications);
    }
}

