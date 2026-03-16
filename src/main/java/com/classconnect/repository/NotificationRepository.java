package com.classconnect.repository;

import com.classconnect.entity.Notification;
import com.classconnect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    
    List<Notification> findByRecipient(User recipient);
    
    List<Notification> findByRecipientAndIsRead(User recipient, Boolean isRead);
    
    List<Notification> findByType(Notification.NotificationType type);
    
    @Query("SELECT n FROM Notification n WHERE n.recipient = :recipient ORDER BY n.createdAt DESC")
    List<Notification> findByRecipientOrderByCreatedAtDesc(@Param("recipient") User recipient);
    
    @Query("SELECT n FROM Notification n WHERE n.recipient = :recipient AND n.isRead = false ORDER BY n.createdAt DESC")
    List<Notification> findUnreadByRecipient(@Param("recipient") User recipient);
    
    @Query("SELECT COUNT(n) FROM Notification n WHERE n.recipient = :recipient AND n.isRead = false")
    Long countUnreadByRecipient(@Param("recipient") User recipient);
    
    @Query("SELECT n FROM Notification n WHERE n.recipient = :recipient AND n.type = :type ORDER BY n.createdAt DESC")
    List<Notification> findByRecipientAndType(@Param("recipient") User recipient, 
                                            @Param("type") Notification.NotificationType type);
    
    @Query("SELECT n FROM Notification n WHERE n.createdAt >= :since ORDER BY n.createdAt DESC")
    List<Notification> findNotificationsSince(@Param("since") LocalDateTime since);
    
    @Query("SELECT n FROM Notification n WHERE n.recipient = :recipient AND n.createdAt >= :since ORDER BY n.createdAt DESC")
    List<Notification> findByRecipientAndCreatedAtAfter(@Param("recipient") User recipient, 
                                                       @Param("since") LocalDateTime since);
}

