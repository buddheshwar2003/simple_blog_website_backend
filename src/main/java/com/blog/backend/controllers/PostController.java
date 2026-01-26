package com.blog.backend.controllers;

import com.blog.backend.domain.dtos.common.PageResponse;
import com.blog.backend.domain.dtos.request.BlogCreateUpdateRequest;
import com.blog.backend.domain.dtos.response.BlogCreateUpdateResponse;
import com.blog.backend.domain.dtos.response.PostDto;
import com.blog.backend.domain.entities.Posts;
import com.blog.backend.services.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@CrossOrigin(origins = "http://localhost:3000" , allowCredentials = "true")
@RestController
@RequestMapping(path = "/post")
public class PostController {
    private final PostService postService;

    public PostController( PostService postService) {
        this.postService = postService;

    }


    @GetMapping("/allposts")
    public ResponseEntity<PageResponse<PostDto>> getAllPost(
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            )
            Pageable pageable
    ) {
        Page<PostDto> page = postService.getAllPosts(pageable);

        PageResponse<PostDto> response = PageResponse.from(page);

        return ResponseEntity.ok(response);
    }
    @GetMapping("/myposts")
    public ResponseEntity<PageResponse<PostDto>> getMyPost(
            @PageableDefault(
            page = 0,
            size = 10,
            sort = "createdAt",
            direction = Sort.Direction.DESC
    )Pageable pageable, @AuthenticationPrincipal UserDetails userDetails){
        Page<PostDto> page = postService.getMyPosts(pageable,userDetails.getUsername());

        PageResponse<PostDto> response = PageResponse.from(page);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable UUID id){
        PostDto response = postService.getPostById(id);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/create")
    public ResponseEntity<BlogCreateUpdateResponse> create(@RequestBody BlogCreateUpdateRequest blog , @AuthenticationPrincipal UserDetails userDetails){
       BlogCreateUpdateResponse savedBlog = postService.createPost(blog,userDetails.getUsername());
       return new ResponseEntity<>(savedBlog, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public  ResponseEntity<BlogCreateUpdateResponse> update(@PathVariable UUID id,@RequestBody BlogCreateUpdateRequest blog , @AuthenticationPrincipal UserDetails userDetails){
        BlogCreateUpdateResponse updatedBlog = postService.updatePost(id,blog,userDetails.getUsername());
        return new ResponseEntity<BlogCreateUpdateResponse>(updatedBlog, HttpStatus.CREATED);

    }

}
