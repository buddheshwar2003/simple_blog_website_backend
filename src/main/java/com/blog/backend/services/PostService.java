package com.blog.backend.services;

import com.blog.backend.domain.PostStatus;
import com.blog.backend.domain.dtos.BlogCreateUpdateRequest;
import com.blog.backend.domain.dtos.BlogCreateUpdateResponse;
import com.blog.backend.domain.entities.Category;
import com.blog.backend.domain.entities.Posts;
import com.blog.backend.domain.entities.User;
import com.blog.backend.mapper.BlogMapper;
import com.blog.backend.repositories.CategoryRepository;
import com.blog.backend.repositories.PostRepository;
import com.blog.backend.repositories.UserRepository;
import org.springframework.stereotype.Service;


@Service
public class PostService {
    private final UserRepository userRepository;
    private final BlogMapper blogMapper;
    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;
    public PostService(UserRepository userRepository, BlogMapper blogMapper, CategoryRepository categoryRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.blogMapper = blogMapper;
        this.categoryRepository = categoryRepository;
        this.postRepository = postRepository;
    }

    public BlogCreateUpdateResponse createPost(BlogCreateUpdateRequest blog , String username){
       User user = userRepository.findByEmail(username).orElseThrow(()-> new RuntimeException("User no found") );
       Category category = categoryRepository.findByName(blog.getCategoryName()).orElseGet(()-> {
           Category category1 = new Category();
           category1.setName(blog.getCategoryName());
           return categoryRepository.save(category1);
       });
        Posts post = blogMapper.toEntity(blog);
        post.setAuthor(user);
        post.setCategory(category);
        post.setStatus(PostStatus.PUBLISHED);
        return blogMapper.toResponse(postRepository.save(post));
    }

}
