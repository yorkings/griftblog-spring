package com.example.griftblog.Controllers;

import com.example.griftblog.DTO.CommentRecord;
import com.example.griftblog.Service.CommentService;
import com.example.griftblog.models.Comment;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/comment")
@RequiredArgsConstructor

public class CommentController {
    private final CommentService commentService;

    @PostMapping("/post/")
    public ResponseEntity<Comment> addComment(@RequestBody CommentRecord comment){
        return ResponseEntity.ok(commentService.createComment( comment));
    }

    @GetMapping("/post")
    public ResponseEntity<List<Comment>>postCommentsWithnoreplies(@RequestParam("id") Long id){
        return  ResponseEntity.ok(commentService.getTopLevelComments(id));
    }

    @GetMapping("post/{id}")
    public ResponseEntity<List<Comment>>postReplies(@PathVariable Long id){
        return  ResponseEntity.ok(commentService.getRepliesForComment(id));
    }

    @GetMapping("/author/{userId}")
    public ResponseEntity<List<Comment>> getCommentsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(commentService.getCommentsByUser(userId));
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Comment>> getCommentsByPost(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getCommentsByPost(postId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok("Comment deleted successfully");
    }


}
