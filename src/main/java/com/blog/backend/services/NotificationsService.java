package com.blog.backend.services;

import com.blog.backend.domain.dtos.response.NotificationCountResponse;
import com.blog.backend.domain.dtos.response.NotificationResponse;
import com.blog.backend.domain.entities.Notifications;
import com.blog.backend.domain.entities.User;
import com.blog.backend.repositories.NotificationsRepository;
import com.blog.backend.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationsService {
    private final NotificationsRepository notificationsRepository;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public NotificationsService(NotificationsRepository notificationsRepository, UserRepository userRepository, SimpMessagingTemplate messagingTemplate) {
        this.notificationsRepository = notificationsRepository;
        this.userRepository = userRepository;
        this.messagingTemplate = messagingTemplate;
    }

    public Page<NotificationResponse> getAllNotice(String username, Pageable pageable) {
        User user = userRepository.findByEmail(username).orElseThrow(()-> new RuntimeException("User not found"));
        Page<Notifications> notifications = notificationsRepository.findByReceiver(user,pageable);
        return notifications.map(notifications1 -> new NotificationResponse(
                notifications1.getSender().getId(),
                notifications1.getId(),
                notifications1.getSender().getName(),
                notifications1.getMessage(),
                notifications1.isRead(),
                notifications1.getCreatedAt(),
                notifications1.getType()
        ));
    }
    @Transactional
    public void markAllRead(String username) {

        User user = userRepository
                .findByEmail(username)
                .orElseThrow();

        List<Notifications> notifications =
                notificationsRepository
                        .findByReceiverAndReadFalse(user);
        notifications.forEach(n ->
                n.setRead(true)
        );
        user.setUnreadNotificationCount(0);
    }

    public NotificationCountResponse getUnreadCount(
            String username
    ) {

        User user = userRepository
                .findByEmail(username)
                .orElseThrow(
                        () -> new RuntimeException(
                                "User not found"
                        )
                );

        long count =user.getUnreadNotificationCount();

        return new NotificationCountResponse(
                count
        );
    }
}
