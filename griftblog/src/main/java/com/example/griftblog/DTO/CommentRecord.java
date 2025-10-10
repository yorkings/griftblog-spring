package com.example.griftblog.DTO;

public record CommentRecord(Long postId,Long authorId,String content, Long parentCommentId) {
}
