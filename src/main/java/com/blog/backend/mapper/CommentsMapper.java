package com.blog.backend.mapper;

import com.blog.backend.domain.dtos.request.CommentRequest;
import com.blog.backend.domain.dtos.response.CommentResponse;
import com.blog.backend.domain.entities.Comments;
import org.springframework.stereotype.Component;

@Component
public class CommentsMapper {
    public Comments toEntity(CommentRequest request){
        Comments comments = new Comments();
        comments.setComment(request.getComment());
        return comments;
    }
    public CommentResponse toResponse(Comments comments){
        return new CommentResponse(comments.getAuthor().getName(),comments.getComment(),comments.getAuthor().getId());
    }
}
