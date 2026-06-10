package com.blog.backend.mapper;

import com.blog.backend.domain.dtos.response.ChatMessageResponse;
import com.blog.backend.domain.dtos.response.MessageResponse;
import com.blog.backend.domain.entities.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {

    public MessageResponse toResponse(
            Message message
    ) {

        return new MessageResponse(
                message.getId(),
                message.getSender().getId(),
                message.getReceiver().getId(),
                message.getSender().getName(),
                message.getContent(),
                message.isSeen(),
                message.getCreatedAt()
        );
    }

    public ChatMessageResponse toSocketResponse(
            Message message
    ) {

        return new ChatMessageResponse(
                message.getId(),
                message.getSender().getId(),
                message.getSender().getName(),
                message.getContent(),
                message.getCreatedAt()
        );
    }
}
