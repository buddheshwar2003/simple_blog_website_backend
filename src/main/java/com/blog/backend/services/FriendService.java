package com.blog.backend.services;

import com.blog.backend.domain.NotificationType;
import com.blog.backend.domain.RequestStatus;
import com.blog.backend.domain.dtos.response.FriendChatResponse;
import com.blog.backend.domain.dtos.response.FriendStatusResponse;
import com.blog.backend.domain.dtos.response.NotificationSocketResponse;
import com.blog.backend.domain.entities.*;
import com.blog.backend.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FriendService {
    private final FriendRepository friendRepository;
    private final FriendRequestRepository friendRequestRepository;
    private final UserRepository userRepository;
    private final NotificationsRepository notificationsRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatRoomRepository chatRoomRepository;
    private final UserActivityService userActivityService;
    public FriendService(FriendRepository friendRepository, FriendRequestRepository friendRequestRepository, UserRepository userRepository, NotificationsRepository notificationsRepository, SimpMessagingTemplate messagingTemplate, ChatRoomRepository chatRoomRepository, UserActivityService userActivityService) {
        this.friendRepository = friendRepository;
        this.friendRequestRepository = friendRequestRepository;
        this.userRepository = userRepository;
        this.notificationsRepository = notificationsRepository;
        this.messagingTemplate = messagingTemplate;
        this.chatRoomRepository = chatRoomRepository;
        this.userActivityService = userActivityService;
    }

    public FriendStatusResponse getFriendStatus(
            UUID profileUserId,
            String currentUsername
    ) {

        User currentUser =
                userRepository.findByEmail(currentUsername)
                        .orElseThrow();

        User profileUser =
                userRepository.findById(profileUserId)
                        .orElseThrow();

        // Already friends?
        if(friendRepository.areFriends(
                currentUser,
                profileUser
        )) {

            return new FriendStatusResponse(
                    "FRIENDS",
                    null
            );
        }

        // I sent request
        Optional<FriendRequest> sent =
                friendRequestRepository
                        .findByReceiverAndSenderAndStatus(
                                profileUser,
                                currentUser,
                                RequestStatus.PENDING
                        );
        if(sent.isPresent()) {

            return new FriendStatusResponse(
                    "PENDING_SENT",
                    sent.get().getId()
            );
        }

        // I received request
        Optional<FriendRequest> received =
                friendRequestRepository
                        .findByReceiverAndSenderAndStatus(
                                currentUser,
                                profileUser,
                                RequestStatus.PENDING
                        );

        if(received.isPresent()) {

            return new FriendStatusResponse(
                    "PENDING_RECEIVED",
                    received.get().getId()
            );
        }

        return new FriendStatusResponse(
                "NONE",
                null
        );
    }


    @Transactional
    public FriendStatusResponse sendFriendRequest(
            UUID profileUserId,
            String username
    ) {

        User currentUser = userRepository
                .findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        User profileUser = userRepository
                .findById(profileUserId)
                .orElseThrow(() -> new RuntimeException("Profile user not found"));

        // Cannot send request to yourself
        if (currentUser.getId().equals(profileUser.getId())) {
            throw new RuntimeException(
                    "You cannot send a friend request to yourself"
            );
        }

        // Check Request Generate or Not
        Optional<FriendRequest> existingRequest =
                friendRequestRepository.findRelationship(
                        currentUser,
                        profileUser
                );

        if (existingRequest.isPresent()) {

            FriendRequest request = existingRequest.get();
            System.out.println("Sender id"+ request.getSender().getId());
            System.out.println("Current User ID "+ currentUser.getId());
            if (request.getSender().getId().equals(currentUser.getId())) {

                return new FriendStatusResponse(
                        "PENDING_SENT",
                        request.getId()
                );
            }

            return new FriendStatusResponse(
                    "PENDING_RECEIVED",
                    request.getId()
            );
        }

        // If already they are friend

        if(friendRepository.areFriends(currentUser, profileUser)){
            return new FriendStatusResponse(
                    "FRIENDS",
                    null
            );
        }


        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSender(currentUser);
        friendRequest.setReceiver(profileUser);
        friendRequest.setStatus(RequestStatus.PENDING);
        FriendRequest saved =
                friendRequestRepository.save(friendRequest);

       //If Someone sent friend request receiver got a notification
        Notifications notification = new Notifications();

        notification.setSender(currentUser);
        notification.setReceiver(profileUser);
        notification.setType(NotificationType.FRIEND_REQUEST);
        notification.setMessage(
                currentUser.getName() + " sent you a friend request"
        );
        profileUser.setUnreadNotificationCount(
                profileUser.getUnreadNotificationCount() + 1
        );
        notificationsRepository.save(notification);
        NotificationSocketResponse response =
                new NotificationSocketResponse(
                        "FRIEND_REQUEST",
                        currentUser.getName() + " sent you a friend request",
                        profileUser.getUnreadNotificationCount()
                );

        messagingTemplate.convertAndSendToUser(
                profileUser.getEmail(),
                "/queue/notifications",
                response
        );
        return new FriendStatusResponse(
                "PENDING_SENT",
                saved.getId()
        );
    }
    @Transactional
    public FriendStatusResponse acceptFriendRequest(
            UUID requestId,
            String username
    ) {

        User currentUser = userRepository
                .findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        FriendRequest request = friendRequestRepository
                .findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        // Only receiver can accept
        System.out.println("Receiver id"+ request.getReceiver().getId());
        System.out.println("Current User ID "+ currentUser.getId());

        if (!request.getReceiver().getId()
                .equals(currentUser.getId())) {

            throw new RuntimeException(
                    "You cannot accept this request"
            );
        }

        if (request.getStatus() != RequestStatus.PENDING) {

            throw new RuntimeException(
                    "Request already Rejected or Accepted"
            );
        }

        // Change if User Accepted The Request
        request.setStatus(RequestStatus.ACCEPTED);

        // Create friendship
        Friend friendship = new Friend();

        friendship.setUser1(request.getSender());
        friendship.setUser2(request.getReceiver());

        friendRepository.save(friendship);

        friendRequestRepository.save(request);
        Notifications notification = new Notifications();

        notification.setSender(currentUser);
        notification.setReceiver(request.getSender());
        notification.setType(NotificationType.FRIEND_ACCEPTED);
        notification.setMessage(
                currentUser.getName() + " accepted a friend request"
        );
        request.getSender().setUnreadNotificationCount(
                request.getSender().getUnreadNotificationCount() + 1
        );

        notificationsRepository.save(notification);
        NotificationSocketResponse response =
                new NotificationSocketResponse(
                        "FRIEND_ACCEPTED",
                        currentUser.getName() + " accepted a friend request",
                        request.getSender().getUnreadNotificationCount()
                );

        messagingTemplate.convertAndSendToUser(
                request.getSender().getEmail(),
                "/queue/notifications",
                response
        );
        ChatRoom room = new ChatRoom();

        room.setUser1(currentUser);
        room.setUser2(request.getSender());

        chatRoomRepository.save(room);
        return new FriendStatusResponse(
                "FRIENDS",
                request.getId()
        );
    }
    @Transactional
    public FriendStatusResponse rejectFriendRequest(
            UUID requestId,
            String username
    ) {

        User currentUser = userRepository
                .findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        FriendRequest request = friendRequestRepository
                .findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        // Only receiver can reject
        if (!request.getReceiver().getId()
                .equals(currentUser.getId())) {

            throw new RuntimeException(
                    "You cannot reject this request"
            );
        }


        if (request.getStatus() != RequestStatus.PENDING) {

            throw new RuntimeException(
                    "Request already Rejected or Accepted"
            );
        }

        request.setStatus(RequestStatus.REJECTED);

        friendRequestRepository.delete(request);

        return new FriendStatusResponse(
                "NONE",
                null
        );
    }
    @Transactional
    public FriendStatusResponse cancelFriendRequest(
            UUID requestId,
            String username
    ) {

        User currentUser = userRepository
                .findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        FriendRequest request = friendRequestRepository
                .findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        // Only sender can cancel request
        if (!request.getSender().getId()
                .equals(currentUser.getId())) {

            throw new RuntimeException(
                    "You cannot cancel this request"
            );
        }


        if (request.getStatus() != RequestStatus.PENDING) {

            throw new RuntimeException(
                    "Request already Rejected or Accepted"
            );
        }

        friendRequestRepository.delete(request);

        return new FriendStatusResponse(
                "NONE",
                null
        );
    }

    public List<FriendChatResponse> getFriends(
            String username
    ){

        User me = userRepository
                .findByEmail(username)
                .orElseThrow();

        List<Friend> friends =
                friendRepository.findAllFriends(me);

        return friends.stream()
                .map(friend -> {

                    User otherUser =
                            friend.getUser1().getId().equals(me.getId())
                                    ? friend.getUser2()
                                    : friend.getUser1();

                    return new FriendChatResponse(otherUser.getId(),otherUser.getName(),userActivityService.isOnline(otherUser.getEmail()),otherUser.getLastSeen());
    }).toList();
}
}
