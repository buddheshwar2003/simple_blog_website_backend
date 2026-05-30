package com.blog.backend.services;

import com.blog.backend.domain.PostStatus;
import com.blog.backend.domain.dtos.request.BlogCreateUpdateRequest;
import com.blog.backend.domain.dtos.request.CommentRequest;
import com.blog.backend.domain.dtos.response.BlogCreateUpdateResponse;
import com.blog.backend.domain.dtos.response.CommentResponse;
import com.blog.backend.domain.dtos.response.PostDto;
import com.blog.backend.domain.entities.Category;
import com.blog.backend.domain.entities.Comments;
import com.blog.backend.domain.entities.Posts;
import com.blog.backend.domain.entities.User;
import com.blog.backend.mapper.BlogMapper;
import com.blog.backend.mapper.CommentsMapper;
import com.blog.backend.mapper.PostMapper;
import com.blog.backend.repositories.CategoryRepository;
import com.blog.backend.repositories.CommentRepository;
import com.blog.backend.repositories.PostRepository;
import com.blog.backend.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Service
public class PostService {
    private final UserRepository userRepository;
    private final BlogMapper blogMapper;
    private final PostMapper postMapper;
    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final CommentsMapper commentsMapper;
    public PostService(UserRepository userRepository, BlogMapper blogMapper, PostMapper postMapper, CategoryRepository categoryRepository, PostRepository postRepository, CommentRepository commentRepository, CommentsMapper commentsMapper) {
        this.userRepository = userRepository;
        this.blogMapper = blogMapper;
        this.postMapper = postMapper;
        this.categoryRepository = categoryRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.commentsMapper = commentsMapper;
    }

    public BlogCreateUpdateResponse createPost(BlogCreateUpdateRequest blog , String username){
       User user = userRepository.findByEmail(username).orElseThrow(()-> new RuntimeException("User no found") );
        String categoryName = blog.getCategory().trim().toLowerCase();

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

    public BlogCreateUpdateResponse updatePost(
            UUID id,
            BlogCreateUpdateRequest blog,
            String username
    ) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Posts post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        // ✅ Authorization check (IMPORTANT)
        if (!post.getAuthor().getId().equals(user.getId())) {
            throw new RuntimeException("You don't have permission to update this post");
        }

        // ✅ Update title only if changed
        if (!Objects.equals(blog.getTitle(), post.getTitle())) {
            post.setTitle(blog.getTitle());
        }

        // ✅ Update content only if changed
        if (!Objects.equals(blog.getContent(), post.getContent())) {
            post.setContent(blog.getContent());
        }

        // ✅ Update category only if changed
        if (blog.getCategory() != null &&
                !Objects.equals(blog.getCategory(), post.getCategory().getName())) {

            String categoryName = blog.getCategory().trim().toLowerCase();

            Category category = categoryRepository.findByName(categoryName)
                    .orElseGet(() -> {
                        Category newCategory = new Category();
                        newCategory.setName(categoryName);
                        return categoryRepository.save(newCategory);
                    });

            post.setCategory(category);
        }

        // ✅ JPA dirty checking saves automatically
        Posts updatedPost = postRepository.save(post);

        return blogMapper.toResponse(updatedPost);
    }

    public void comment(CommentRequest request, String username) {
        User user = userRepository.findByEmail(username).orElseThrow(()-> new RuntimeException("User no found") );
        Posts posts = postRepository.findById(request.getPostId()).orElseThrow(()-> new RuntimeException("Post not found"));
        Comments comments = commentsMapper.toEntity(request);
        comments.setPost(posts);
        comments.setAuthor(user);
        commentRepository.save(comments);
    }

    public Page<CommentResponse> getComments(Pageable pageable, UUID id) {
        Posts posts = postRepository.findById(id).orElseThrow(()-> new RuntimeException("Post not found"));
        Page<Comments> comments = commentRepository.findAllByPostId(id,pageable);
        return comments.map(commentsMapper::toResponse);
    }
}
