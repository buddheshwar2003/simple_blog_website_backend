package com.blog.backend.controllers;

import com.blog.backend.domain.dtos.common.PageResponse;
import com.blog.backend.domain.dtos.response.NotificationCountResponse;
import com.blog.backend.domain.dtos.response.NotificationResponse;
import com.blog.backend.domain.dtos.response.PostDto;
import com.blog.backend.services.NotificationsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationsController {
    private final NotificationsService notificationsService;

    public NotificationsController(NotificationsService notificationsService) {
        this.notificationsService = notificationsService;
    }
    @GetMapping("/getAll")
    public ResponseEntity<PageResponse<NotificationResponse>> getAllNotification(@AuthenticationPrincipal UserDetails userDetails, @PageableDefault(page = 0,
            size = 10,
            sort = "createdAt",
            direction = Sort.Direction.DESC)Pageable pageable){
        Page<NotificationResponse> page= notificationsService.getAllNotice(userDetails.getUsername(),pageable);
        PageResponse<NotificationResponse> response = PageResponse.from(page);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/read-all")
    public ResponseEntity<Void> markAllRead(
            @AuthenticationPrincipal UserDetails userDetails
    ) {

        notificationsService.markAllRead(
                userDetails.getUsername()
        );

        return ResponseEntity.ok().build();
    }
    @GetMapping("/count")
    public ResponseEntity<NotificationCountResponse> getUnreadCount(
            @AuthenticationPrincipal UserDetails userDetails
    ) {

        NotificationCountResponse response =
                notificationsService.getUnreadCount(
                        userDetails.getUsername()
                );

        return ResponseEntity.ok(response);
    }
}
