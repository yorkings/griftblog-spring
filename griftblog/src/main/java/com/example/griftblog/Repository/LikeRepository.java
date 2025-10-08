package com.example.griftblog.Repository;

import com.example.griftblog.models.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like,Long> {
    Optional<Like> findByPostIdAndUserId(Long postId, Long userId);

    // Get all likes for a specific post (useful for counting likes)
    List<Like> findByPostId(Long postId);

    List<Like> findByUserId(Long userId);
}
