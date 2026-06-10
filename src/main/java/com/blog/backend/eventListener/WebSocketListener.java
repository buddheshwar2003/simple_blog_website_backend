package com.blog.backend.eventListener;

import com.blog.backend.services.UserActivityService;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;

@Component
public class WebSocketListener {

    private final UserActivityService userActivityService;

    public WebSocketListener(UserActivityService userActivityService) {
        this.userActivityService = userActivityService;
    }

    @EventListener
    public void handleConnect(
            SessionConnectedEvent event
    ) {

        StompHeaderAccessor accessor =
                StompHeaderAccessor.wrap(event.getMessage());

        Principal principal = accessor.getUser();

        if(principal != null){
            userActivityService.userConnected(
                    principal.getName()
            );
        }
    }

    @EventListener
    public void handleDisconnect(
            SessionDisconnectEvent event
    ) {

        StompHeaderAccessor accessor =
                StompHeaderAccessor.wrap(event.getMessage());

        Principal principal = accessor.getUser();

        if(principal != null){
            userActivityService.userDisconnected(
                    principal.getName()
            );
        }
    }
}
