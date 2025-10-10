package com.example.griftblog.Service;

import com.example.griftblog.Repository.LikeRepository;
import com.example.griftblog.Repository.PostRepository;
import com.example.griftblog.Repository.UserRepository;
import com.example.griftblog.exceptions.ResourceNotFound;
import com.example.griftblog.models.Like;
import com.example.griftblog.models.Post;
import com.example.griftblog.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepo;
    private final PostRepository postRepo;
    private final UserRepository userRepo;

    @Transactional
    public boolean toggleLike(Long postId, Long userId) {
        Optional<Like> existingLike = likeRepo.findByPostIdAndUserId(postId, userId);
        if (existingLike.isPresent()) {
            likeRepo.delete(existingLike.get());
            return  false;
        }
        else{
            Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFound("Post not found"));
            User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFound("User not found"));
            Like addLike=Like.builder()
                    .post(post)
                    .user(user)
                    .build();
            likeRepo.save(addLike);
            return true;
        }
    }
    public Long getLikeCountForPost(Long postId) {
        return (long) likeRepo.findByPostId(postId).size();
    }

}
