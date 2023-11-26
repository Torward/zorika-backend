package ru.lomov.zorika_backend.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostReplyRequest {
    private String content;
    private String image;
    private Long postId;
    private LocalDateTime createdAt;
}
