package com.blog.backend.repositories;

import com.blog.backend.domain.entities.ChatRoom;
import com.blog.backend.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, UUID> {

    @Query("""
        SELECT c
        FROM ChatRoom c
        WHERE
        (c.user1 = :user1 AND c.user2 = :user2)
        OR
        (c.user1 = :user2 AND c.user2 = :user1)
    """)
    Optional<ChatRoom> findRoom(
            @Param("user1") User user1,
            @Param("user2") User user2
    );
}
