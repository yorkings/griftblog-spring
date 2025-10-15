package com.example.griftblog.Service;

import com.example.griftblog.DTO.PostRecord;
import com.example.griftblog.DTO.Status;
import com.example.griftblog.Repository.CategoryRepository;
import com.example.griftblog.Repository.LikeRepository;
import com.example.griftblog.Repository.PostRepository;
import com.example.griftblog.Repository.UserRepository;
import com.example.griftblog.exceptions.ResourceNotFound;
import com.example.griftblog.models.Category;
import com.example.griftblog.models.Post;
import com.example.griftblog.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepo;
    private final LikeRepository likeRepo;
    private final CategoryRepository catRepo;
    private  final UserRepository userRepo;

    @Transactional
    public Post createPost(PostRecord record) {
        User author = userRepo.findById(record.authorId())
                .orElseThrow(()->new ResourceNotFound("Author not found in ID " + record.authorId()));
        Category category=catRepo.findById(record.categoryId())
                .orElseThrow(()->new ResourceNotFound("Category not found in ID: "+record.categoryId()));
        String slug = generateSlug(record.title());
        Post newPost=Post.builder().author(author)
                .title(record.title())
                .slug(slug)
                .category(category)
                .author(author)
                .content(record.content())
                .status(record.status())
                .imageURl(record.imageUrl())
                .createdAt(LocalDateTime.now())
                .build();
        return postRepo.save(newPost);
    }
    // CRUD
    @Transactional
    public Post updatePost(PostRecord record,Long postId){
        Post post =postRepo.findById(postId).orElseThrow(()->new ResourceNotFound("Resouce not found"));
        Category category=catRepo.findById(record.categoryId()).orElseThrow(()->new ResourceNotFound("Resouce not found"));
        // Update fields
        post.setTitle(record.title());
        post.setCategory(category);
        post.setUpdatedAt(LocalDateTime.now());
        post.setContent(record.content());
        post.setStatus(record.status());
        post.setSlug(generateUniqueSlug(record.title()));
        post.setImageURl(record.imageUrl());
        return postRepo.save(post);
    };

    public void deletePost(Long postId) {
        postRepo.deleteById(postId);
    }

    //Retrieval
    public List<Post>getAllPosts(){
         return postRepo.findAll();
    }
    public List<Post> findByStatus(Status status){
        return postRepo.findByStatus(status);
    }

    public Post getPostById(Long postId) {
        return postRepo.findById(postId).orElseThrow(()->new ResourceNotFound("no id found"));
    }

    public List<Post> searchTitle(String name){
        return postRepo.findByTitleContainingIgnoreCase(name);
    }

    public List<Post> getAuthorPosts(Long userId) {
        return postRepo.findByAuthorId(userId);
    }
    public List<Post>getAuthoridandStatus(Long AuthorId,Status status){
        return postRepo.findByAuthorIdAndStatus(AuthorId,status);
    }
    public List<Post> getPublishedPosts() {
        return postRepo.findByStatusOrderByCreatedAtDesc(Status.PUBLISHED);
    }
    public  List<Post>getbyCategory(Long id){
        return  postRepo.findByCategoryId(id);
    }

    // methods
    public String generateSlug(String title) {
        // Basic slug generation: replaces non-alphanumeric chars with hyphens
        return title.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-")
                .replaceAll("^-|-$", "");
    }
    private String generateUniqueSlug(String title) {
        String base = generateSlug(title);
        String slug = base;
        int counter = 1;
        while (postRepo.findBySlug(slug).isPresent()) {
            slug = base + "-" + counter++;
        }
        return slug;
    }


}
