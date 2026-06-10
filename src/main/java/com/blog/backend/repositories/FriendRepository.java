package com.blog.backend.repositories;

import com.blog.backend.domain.entities.Friend;
import com.blog.backend.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FriendRepository extends JpaRepository<Friend, UUID> {
    @Query("""
         SELECT COUNT(f) > 0
         FROM Friend f
         WHERE
          (f.user1 = :user1 AND f.user2 = :user2)
        OR
          (f.user1 = :user2 AND f.user2 = :user1)
        """)
    boolean areFriends(User user1, User user2);

    @Query("""
        SELECT f
        FROM Friend f
        WHERE f.user1 = :user
           OR f.user2 = :user
    """)
    List<Friend> findAllFriends(
            @Param("user") User user
    );
}
