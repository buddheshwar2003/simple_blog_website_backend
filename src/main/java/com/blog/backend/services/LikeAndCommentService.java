package com.blog.backend.services;

import com.blog.backend.domain.NotificationType;
import com.blog.backend.domain.dtos.request.CommentRequest;
import com.blog.backend.domain.dtos.response.CommentResponse;
import com.blog.backend.domain.entities.*;
import com.blog.backend.mapper.BlogMapper;
import com.blog.backend.mapper.CommentsMapper;
//import com.blog.backend.mapper.PostMapper;
import com.blog.backend.repositories.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class LikeAndCommentService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final CommentsMapper commentsMapper;
    private final LikeRepository likeRepository;
    private final NotificationsRepository notificationsRepository;

    public LikeAndCommentService(UserRepository userRepository, PostRepository postRepository, CommentRepository commentRepository, CommentsMapper commentsMapper, LikeRepository likeRepository, NotificationsRepository notificationsRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.commentsMapper = commentsMapper;
        this.likeRepository = likeRepository;
        this.notificationsRepository = notificationsRepository;
    }

    public CommentResponse comment(CommentRequest request, String username) {
        User user = userRepository.findByEmail(username).orElseThrow(()-> new RuntimeException("User no found") );
        Posts posts = postRepository.findById(request.getPostId()).orElseThrow(()-> new RuntimeException("Post not found"));
        Comments comments = commentsMapper.toEntity(request);
        posts.setCommentCount(posts.getCommentCount()+1);
        comments.setPost(posts);
        comments.setAuthor(user);
        Comments saveComment = commentRepository.save(comments);
        postRepository.save(posts);
        Notifications notification = new Notifications();

        notification.setSender(user);
        notification.setReceiver(posts.getAuthor());
        notification.setType(NotificationType.COMMENT);
        notification.setMessage(
                user.getName() + " comment on you post"
        );
        User receiver = posts.getAuthor();

        receiver.setUnreadNotificationCount(
                receiver.getUnreadNotificationCount() + 1
        );

        notificationsRepository.save(notification);
        return commentsMapper.toResponse(saveComment);
    }

    public Page<CommentResponse> getComments(Pageable pageable, UUID id) {
        Posts posts = postRepository.findById(id).orElseThrow(()-> new RuntimeException("Post not found"));
        Page<Comments> comments = commentRepository.findAllByPostId(id,pageable);
        return comments.map(commentsMapper::toResponse);
    }

    public void likePost(UUID id, String username) {
        User user = userRepository.findByEmail(username).orElseThrow(()-> new RuntimeException("User no found") );
        Posts post = postRepository.findById(id).orElseThrow(()-> new RuntimeException("Post not found"));
        Optional<Likes> existingLike = likeRepository.findByUserAndPost(user, post);
        if (existingLike.isPresent()){
            likeRepository.delete(existingLike.get());
            post.setLikeCount(post.getLikeCount()-1);
        }else {
            Likes likes = new Likes();
            likes.setPost(post);
            likes.setUser(user);
            likeRepository.save(likes);
            post.setLikeCount(post.getLikeCount()+1);
            if(!user.getId().equals(
                    post.getAuthor().getId())) {

                Notifications notification = new Notifications();

                notification.setSender(user);
                notification.setReceiver(post.getAuthor());
                notification.setType(NotificationType.LIKE);
                notification.setMessage(
                        user.getName() + " like on you post"
                );

                User receiver = post.getAuthor();

                receiver.setUnreadNotificationCount(
                        receiver.getUnreadNotificationCount() + 1
                );

                notificationsRepository.save(notification);
            }

        }
        postRepository.save(post);
    }
}
