package com.example.griftblog.Repository;

import com.example.griftblog.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageReposirory  extends JpaRepository<Image,Long> {
    List<Image> findByPostId(Long postId);
}
