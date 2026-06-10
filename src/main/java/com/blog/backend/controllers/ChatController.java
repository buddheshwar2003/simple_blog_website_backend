package com.blog.backend.controllers;

import com.blog.backend.domain.dtos.common.PageResponse;
import com.blog.backend.domain.dtos.request.ChatMessageRequest;
import com.blog.backend.domain.dtos.response.MessageResponse;
import com.blog.backend.services.ChatService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.UUID;

@RestController
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @MessageMapping("/chat.send")
    public void sendMessage(
            ChatMessageRequest request,
            Principal principal
    ) {
        System.out.println("MESSAGE RECEIVED");
        chatService.sendMessage(
                request,
                principal.getName()
        );
    }
    @GetMapping("/chat/{friendId}")
    public ResponseEntity<PageResponse<MessageResponse>> getMessages(
            @PathVariable UUID friendId,
            @AuthenticationPrincipal UserDetails userDetails,
            @PageableDefault(
                    page = 0,
                    size = 20,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            )
            Pageable pageable
    ) {

        Page<MessageResponse> page =
                chatService.getMessages(
                        friendId,
                        userDetails.getUsername(),
                        pageable
                );

        return ResponseEntity.ok(
                PageResponse.from(page)
        );
    }
}