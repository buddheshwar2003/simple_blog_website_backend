package com.blog.backend.repositories;

import com.blog.backend.domain.RequestStatus;
import com.blog.backend.domain.entities.FriendRequest;
import com.blog.backend.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, UUID> {
    Optional<FriendRequest> findByReceiverAndSenderAndStatus(
            User receiver,
            User sender,
            RequestStatus status
    );
    boolean existsBySenderAndReceiver(
            User sender,
            User receiver
    );
    Optional<FriendRequest> findBySenderAndReceiver(
            User sender,
            User receiver
    );

    @Query("""
    SELECT fr
    FROM FriendRequest fr
    WHERE
        (fr.sender = :user1 AND fr.receiver = :user2)
        OR
        (fr.sender = :user2 AND fr.receiver = :user1)
    """)
    Optional<FriendRequest> findRelationship(
            @Param("user1") User user1,
            @Param("user2") User user2
    );
}
