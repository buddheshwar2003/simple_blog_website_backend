package com.blog.backend.controllers;

import com.blog.backend.domain.dtos.common.PageResponse;
import com.blog.backend.domain.dtos.request.CommentRequest;
import com.blog.backend.domain.dtos.response.CommentResponse;
import com.blog.backend.services.LikeAndCommentService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/post")
public class LikeAndCommentController {

    private final LikeAndCommentService likeAndCommentService;

    public LikeAndCommentController(LikeAndCommentService likeAndCommentService) {
        this.likeAndCommentService = likeAndCommentService;

    }

    @PostMapping("/comment")
    public ResponseEntity<CommentResponse> comment(@AuthenticationPrincipal UserDetails userDetails, @RequestBody CommentRequest request){
       CommentResponse comment = likeAndCommentService.comment(request,userDetails.getUsername());
       return new ResponseEntity<>(comment,HttpStatus.OK);
    }
    @GetMapping("/comments/{id}")
    public ResponseEntity<PageResponse<CommentResponse>> getComment(@PathVariable UUID id, @PageableDefault(
            page = 0,
            size = 10,
            sort = "createdAt",
            direction = Sort.Direction.DESC
    ) Pageable pageable){
        return new ResponseEntity<>(PageResponse.from(likeAndCommentService.getComments(pageable,id)), HttpStatus.OK);
    }

    @PostMapping("/likes/{id}")
    public void like(@PathVariable UUID id,@AuthenticationPrincipal UserDetails userDetails){
        likeAndCommentService.likePost(id,userDetails.getUsername());
    }

}
