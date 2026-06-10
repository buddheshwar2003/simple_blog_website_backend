package com.blog.backend.repositories;

import com.blog.backend.domain.entities.Notifications;
import com.blog.backend.domain.entities.Posts;
import com.blog.backend.domain.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface NotificationsRepository extends JpaRepository<Notifications, UUID> {
    Page<Notifications> findByReceiver(User user, Pageable pageable);
    List<Notifications>
    findByReceiverAndReadFalse(
            User receiver
    );
    long countByReceiverAndReadFalse(
            User receiver
    );
}
