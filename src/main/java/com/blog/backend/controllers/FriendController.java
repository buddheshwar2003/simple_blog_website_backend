package com.blog.backend.controllers;

import com.blog.backend.domain.dtos.response.FriendChatResponse;
import com.blog.backend.domain.dtos.response.FriendStatusResponse;
import com.blog.backend.services.FriendService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/friend")
public class FriendController {

    private final FriendService friendService;

    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    @GetMapping("/status/{userId}")
    public ResponseEntity<FriendStatusResponse> getStatus(
            @PathVariable UUID userId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {

        return ResponseEntity.ok(
                friendService.getFriendStatus(
                        userId,
                        userDetails.getUsername()
                )
        );
    }
    @PostMapping("/request/{id}")
    public ResponseEntity<FriendStatusResponse> sentRequest(@PathVariable UUID id,@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok(
                friendService.sendFriendRequest(
                        id,
                        userDetails.getUsername()
                )
        );
    }
    @PutMapping("/accept/{id}")
    public ResponseEntity<FriendStatusResponse> acceptRequest(@PathVariable UUID id,@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok(
                friendService.acceptFriendRequest(
                        id,
                        userDetails.getUsername()
                )
        );
    }
    @DeleteMapping("/decline/{id}")
    public ResponseEntity<FriendStatusResponse> rejectRequest(@PathVariable UUID id,@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok(
                friendService.rejectFriendRequest(
                        id,
                        userDetails.getUsername()
                )
        );
    }
    @DeleteMapping("/cancel/{userId}")
    public ResponseEntity<FriendStatusResponse> cancelRequest(@PathVariable UUID userId,@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok(
                friendService.cancelFriendRequest(
                        userId,
                        userDetails.getUsername()
                )
        );
    }
    @GetMapping("/chat/friends")
    public List<FriendChatResponse> getFriends(
            @AuthenticationPrincipal UserDetails userDetails
    ){
        return friendService.getFriends(userDetails.getUsername());
    }
}
