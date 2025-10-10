package com.example.griftblog.Service;

import com.example.griftblog.DTO.CommentRecord;
import com.example.griftblog.Repository.CommentRepository;
import com.example.griftblog.Repository.PostRepository;
import com.example.griftblog.Repository.UserRepository;
import com.example.griftblog.exceptions.ResourceNotFound;
import com.example.griftblog.models.Comment;
import com.example.griftblog.models.Post;
import com.example.griftblog.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commRepo;
    private final PostRepository postRepo;
    private final UserRepository userRepo;

    @Transactional
    public Comment createComment(CommentRecord request){
        Post post=postRepo.findById(request.postId()).orElseThrow(()->new ResourceNotFound("post not found"));
        User user=userRepo.findById(request.authorId()).orElseThrow(()->new ResourceNotFound("user not found"));
        Comment parentComment = null;
        if (request.parentCommentId() != null) {
            parentComment = commRepo.findById(request.parentCommentId()).orElseThrow(() -> new ResourceNotFound("Parent comment not found"));
        }
        Comment newComment=Comment.builder()
                .post(post)
                .author(user)
                .parentComment(parentComment)
                .content(request.content())
                .build();

        return  commRepo.save(newComment);
    }

    // --- Retrieval Logic ---
    public List<Comment> getTopLevelComments(Long postId) {
        return commRepo.findByPostIdAndParentCommentIsNull(postId);
    }

    public List<Comment> getRepliesForComment(Long parentCommentId) {
        return commRepo.findByParentCommentId(parentCommentId);
    }
}
