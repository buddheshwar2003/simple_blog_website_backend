package com.blog.backend.domain.dtos.request;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BlogCreateUpdateRequest {
    private String title;
    private String content;
    private String category;
    private MultipartFile image;
}
