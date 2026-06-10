//package com.blog.backend.mapper;
//
//import com.blog.backend.domain.dtos.response.PostDto;
//import com.blog.backend.domain.entities.Posts;
//import org.springframework.stereotype.Component;
//
//@Component
//public class PostMapper {
//    public PostDto toResponseDto(Posts posts){
//        if(posts == null){
//            return null;
//        }
//        return new PostDto(posts.getId(),posts.getAuthor().getEmail()
//                ,posts.getAuthor().getName()
//                ,posts.getTitle()
//        ,posts.getContent(),posts.getCategory().getName(),posts.getUpdatedAt(),posts.getLikeCount(),
//                posts.getCommentCount());
//   }
//}
