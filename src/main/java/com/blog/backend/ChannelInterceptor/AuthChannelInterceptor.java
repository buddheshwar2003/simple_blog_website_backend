package com.blog.backend.ChannelInterceptor;

import com.blog.backend.JWT.JwtUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthChannelInterceptor
        implements ChannelInterceptor {

    private final JwtUtil jwtService;

    public AuthChannelInterceptor(
            JwtUtil jwtService
    ) {
        this.jwtService = jwtService;
    }

    @Override
    public Message<?> preSend(
            @NotNull Message<?> message,
            @NotNull MessageChannel channel
    ) {

        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(
                        message,
                        StompHeaderAccessor.class
                );

        assert accessor != null;
        if (StompCommand.CONNECT.equals(
                accessor.getCommand()
        )) {

            String authHeader =
                    accessor.getFirstNativeHeader(
                            "Authorization"
                    );

            if (authHeader != null &&
                    authHeader.startsWith("Bearer ")) {

                String token =
                        authHeader.substring(7);

                String email =
                        jwtService.extractUsername(
                                token
                        );

                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(
                                email,
                                null,
                                List.of()
                        );

                accessor.setUser(authentication);
            }
        }

        return message;
    }
}
