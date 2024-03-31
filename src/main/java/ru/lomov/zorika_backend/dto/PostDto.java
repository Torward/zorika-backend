package ru.lomov.zorika_backend.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Data
@Builder
public class PostDto {
    private Long id;
    private String content;
    private String image;
    private String video;
    private UserDto user;
    private LocalDateTime createdAt;
    private int totalLikes;
    private int totalReplies;
    private int totalReposts;
    private boolean isLiked;
    private boolean isReposted;
    private List<Long> repostUsersId;
    private List<PostDto> replyPosts;
}
