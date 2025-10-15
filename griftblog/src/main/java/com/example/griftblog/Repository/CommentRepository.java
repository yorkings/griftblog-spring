package com.example.griftblog.Repository;

import com.example.griftblog.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByPostIdOrderByCreatedAtDesc(Long postId);
    List<Comment> findByAuthorIdOrderByCreatedAtDesc(Long authorId);

    List<Comment>findByPostIdAndParentCommentIsNull(Long postId);
    List<Comment> findByParentCommentId(Long parentCommentId);

}
