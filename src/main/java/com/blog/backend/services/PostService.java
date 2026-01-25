package com.blog.backend.services;

import com.blog.backend.domain.PostStatus;
import com.blog.backend.domain.dtos.request.BlogCreateUpdateRequest;
import com.blog.backend.domain.dtos.response.BlogCreateUpdateResponse;
import com.blog.backend.domain.dtos.response.PostDto;
import com.blog.backend.domain.entities.Category;
import com.blog.backend.domain.entities.Posts;
import com.blog.backend.domain.entities.User;
import com.blog.backend.mapper.BlogMapper;
import com.blog.backend.mapper.PostMapper;
import com.blog.backend.repositories.CategoryRepository;
import com.blog.backend.repositories.PostRepository;
import com.blog.backend.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class PostService {
    private final UserRepository userRepository;
    private final BlogMapper blogMapper;
    private final PostMapper postMapper;
    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;
    public PostService(UserRepository userRepository, BlogMapper blogMapper, PostMapper postMapper, CategoryRepository categoryRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.blogMapper = blogMapper;
        this.postMapper = postMapper;
        this.categoryRepository = categoryRepository;
        this.postRepository = postRepository;
    }

    public BlogCreateUpdateResponse createPost(BlogCreateUpdateRequest blog , String username){
       User user = userRepository.findByEmail(username).orElseThrow(()-> new RuntimeException("User no found") );
        String categoryName = blog.getCategoryName().trim().toLowerCase();

        Category category = categoryRepository.findByName(categoryName)
                .orElseGet(() -> {
                    Category newCategory = new Category();
                    newCategory.setName(categoryName);
                    return categoryRepository.save(newCategory);
                });

        Posts post = blogMapper.toEntity(blog);
        post.setAuthor(user);
        post.setCategory(category);
        post.setStatus(PostStatus.PUBLISHED);
        return blogMapper.toResponse(postRepository.save(post));
    }

    public Page<PostDto> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable).map(postMapper::toResponseDto);
    }

    public Page<PostDto> getMyPosts(Pageable pageable, String username) {
        User user = userRepository.findByEmail(username).orElseThrow(()-> new RuntimeException("Uer not found"));
        return postRepository.findByAuthorId(user.getId(),pageable).map(postMapper::toResponseDto);
    }

    public PostDto getPostById(UUID id) {
        Posts posts = postRepository.findById(id).orElseThrow(()-> new RuntimeException("Post not Found"));
        return postMapper.toResponseDto(posts);
    }
}
