package com.example.griftblog.DTO;

public record PostRecord(Long authorId, Long categoryId, String title, String content,Status status,String imageUrl){

}