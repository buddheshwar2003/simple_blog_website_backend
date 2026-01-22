package com.blog.backend.controllers;

import com.blog.backend.domain.dtos.BlogCreateUpdateRequest;
import com.blog.backend.domain.dtos.BlogCreateUpdateResponse;
import com.blog.backend.services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:3000" , allowCredentials = "true")
@RestController
@RequestMapping(path = "/post")
public class PostController {
    private final PostService postService;

    public PostController( PostService postService) {
        this.postService = postService;

    }

    @PostMapping("/create")
    public ResponseEntity<BlogCreateUpdateResponse> create(@RequestBody BlogCreateUpdateRequest blog , @AuthenticationPrincipal UserDetails userDetails){
       BlogCreateUpdateResponse savedBlog = postService.createPost(blog,userDetails.getUsername());
       return new ResponseEntity<>(savedBlog, HttpStatus.CREATED);
    }

}
