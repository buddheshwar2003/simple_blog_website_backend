package com.blog.backend.mapper;

import com.blog.backend.domain.dtos.BlogCreateUpdateRequest;
import com.blog.backend.domain.dtos.BlogCreateUpdateResponse;
import com.blog.backend.domain.entities.Posts;
import org.springframework.stereotype.Component;

@Component
public class BlogMapper {

    public Posts toEntity(BlogCreateUpdateRequest request){
        Posts post = new Posts();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());

        return post;

    }

    public BlogCreateUpdateResponse toResponse(Posts post){
        BlogCreateUpdateResponse createdBlog = new BlogCreateUpdateResponse();
        createdBlog.setAuthorName(post.getAuthor().getName());
        createdBlog.setCategoryName(post.getCategory().getName());
        createdBlog.setLastUpdate(post.getUpdatedAt());
        createdBlog.setId(post.getId());
        createdBlog.setTitle(post.getTitle());
        createdBlog.setContent(post.getContent());
        return createdBlog;
    }
}
