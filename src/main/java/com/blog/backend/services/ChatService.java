package com.blog.backend.services;

import com.blog.backend.domain.dtos.request.ChatMessageRequest;
import com.blog.backend.domain.dtos.response.MessageResponse;
import com.blog.backend.domain.entities.ChatRoom;
import com.blog.backend.domain.entities.Message;
import com.blog.backend.domain.entities.User;
import com.blog.backend.mapper.MessageMapper;
import com.blog.backend.repositories.ChatRoomRepository;
import com.blog.backend.repositories.MessageRepository;
import com.blog.backend.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class ChatService {
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MessageRepository messageRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final MessageMapper messageMapper;
    public ChatService(UserRepository userRepository, ChatRoomRepository chatRoomRepository, MessageRepository messageRepository, SimpMessagingTemplate messagingTemplate, MessageMapper messageMapper) {
        this.userRepository = userRepository;
        this.chatRoomRepository = chatRoomRepository;
        this.messageRepository = messageRepository;
        this.messagingTemplate = messagingTemplate;
        this.messageMapper = messageMapper;
    }

    @Transactional
    public void sendMessage(
            ChatMessageRequest request,
            String email
    ) {
        System.out.println("In Chat");
        User sender =
                userRepository.findByEmail(email)
                        .orElseThrow();

        User receiver =
                userRepository.findById(
                        request.receiverId()
                ).orElseThrow();

        ChatRoom room =
                chatRoomRepository.findRoom(
                        sender,
                        receiver
                ).orElseThrow();

        Message message = new Message();

        message.setRoom(room);
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(
                request.message()
        );

        messageRepository.save(message);

        messagingTemplate.convertAndSendToUser(
                receiver.getEmail(),
                "/queue/chat",
                messageMapper.toResponse(message)
        );
    }

    public Page<MessageResponse> getMessages(
            UUID friendId,
            String username,
            Pageable pageable
    ) {

        User currentUser =
                userRepository
                        .findByEmail(username)
                        .orElseThrow();

        User friend =
                userRepository
                        .findById(friendId)
                        .orElseThrow();

        ChatRoom room =
                chatRoomRepository
                        .findRoom(
                                currentUser,
                                friend
                        )
                        .orElseThrow();

        Page<MessageResponse> page = messageRepository
                .findByRoomIdOrderByCreatedAtDesc(
                        room.getId(),
                        pageable
                )
                .map(messageMapper::toResponse);

        List<MessageResponse> list = new ArrayList<>(page.getContent());
        Collections.reverse(list);

        return new PageImpl<>(
                list,
                pageable,
                page.getTotalElements()
        );
    }
}
