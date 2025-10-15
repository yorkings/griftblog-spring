package com.example.griftblog.Controllers;

import com.example.griftblog.DTO.PostRecord;
import com.example.griftblog.DTO.Status;
import com.example.griftblog.Service.CategoryService;
import com.example.griftblog.Service.CommentService;
import com.example.griftblog.Service.LikeService;
import com.example.griftblog.Service.PostService;
import com.example.griftblog.models.Like;
import com.example.griftblog.models.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/posts")
public class PostController {
    private final PostService postService;
    private final CommentService commentService;
    private final CategoryService categoryService;
    private  final LikeService likeService;

    @GetMapping("/list")
    public ResponseEntity<List<Post>>listpost(){
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }
    //crud

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody PostRecord record) {
        return ResponseEntity.ok(postService.createPost(record));
    }

    @PutMapping("/{id}")
    public  ResponseEntity<Post>updatePost(@RequestBody PostRecord record,@PathVariable Long id){
        return  ResponseEntity.ok(postService.updatePost(record,id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok("Post deleted successfully");
    }

    //extra retrievals
    @GetMapping("/status")
    public  ResponseEntity<List<Post>>getByStatus(@RequestParam("value") Status status){
        return  ResponseEntity.ok(postService.findByStatus(status));
    }

    @GetMapping("/author/{id}")
    public  ResponseEntity<List<Post>>getByAuthor(@PathVariable Long id){
       return  ResponseEntity.ok(postService.getAuthorPosts(id));
    }

    @GetMapping("/name/{name}")
    public  ResponseEntity<List<Post>>SearchTitle(@PathVariable String name){
        return  ResponseEntity.ok(postService.searchTitle(name));
    }
    @GetMapping("/published")
    public  ResponseEntity<List<Post>>publishedPosts(){
        return  ResponseEntity.ok(postService.getPublishedPosts());
    }
    @GetMapping("/author/status/{id}")
    public  ResponseEntity<List<Post>>authorPostStatus(@PathVariable Long id,@RequestParam("status") Status status){
        return  ResponseEntity.ok(postService.getAuthoridandStatus(id,status));
    }

    @GetMapping("/category/{id}")
    public  ResponseEntity<List<Post>>categoryPosts(@PathVariable Long id){
        return  ResponseEntity.ok(postService.getbyCategory(id));
    }


    @PostMapping("/like/{postId}")
    public ResponseEntity<Boolean>changeLike(@PathVariable Long postId,@RequestParam("userId") Long userId ){
        return  ResponseEntity.ok(likeService.toggleLike(postId,userId));
    }
    @GetMapping("/like/{postId}")
    public  ResponseEntity<Long>likeCounts(@PathVariable Long postId){
        return  ResponseEntity.ok(likeService.getLikeCountForPost(postId));
    }
}
