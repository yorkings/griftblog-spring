package com.example.griftblog.Repository;

import com.example.griftblog.data.Status;
import com.example.griftblog.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByStatus(Status status);
    List<Post> findByAuthorId(Long userId);
    List<Post> findByTitleContainingIgnoreCase(String keyword);
    Optional<Post> findBySlug(String slug);
    List<Post> findByCategoryId(Long categoryId);
    List<Post> findByStatusOrderByCreatedAtDesc(Status status);
}
