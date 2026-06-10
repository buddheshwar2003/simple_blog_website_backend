package com.blog.backend.domain.dtos.response;

import java.time.LocalDateTime;
import java.util.UUID;

public class FriendChatResponse {

    private UUID id;
    private String name;

    private boolean online;
    private LocalDateTime lastSeen;


    public FriendChatResponse(UUID id, String name, boolean online, LocalDateTime lastSeen) {
        this.id = id;
        this.name = name;
        this.online = online;
        this.lastSeen = lastSeen;
    }

    public FriendChatResponse() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public LocalDateTime getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(LocalDateTime lastSeen) {
        this.lastSeen = lastSeen;
    }

}
