package ru.lomov.zorika_backend.utils;

import ru.lomov.zorika_backend.dto.PostDto;
import ru.lomov.zorika_backend.dto.UserDto;
import ru.lomov.zorika_backend.entities.AppUser;
import ru.lomov.zorika_backend.entities.Post;

import java.util.ArrayList;
import java.util.List;

public class PostDtoMapper {
    public static PostDto postToDto(Post post, AppUser reqUser){
        UserDto userDto = UserDtoMapper.userToDto(post.getAppUser());
        boolean isLiked = PostUtil.isLikedByReqUser(reqUser, post);
        boolean isReposted = PostUtil.isRepostedByReqUser(reqUser, post);
        List<Long> repostUserId = new ArrayList<>();
        for (AppUser user: post.getRepostAppUser()) {
            repostUserId.add(user.getUserId());
        }
        PostDto postDto = PostDto.builder()
                .id(post.getPostId())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .image(post.getImage())
                .totalLikes(post.getAppLikes().size())
                .totalReplies(post.getReplyPosts().size())
                .totalReposts(post.getRepostAppUser().size())
                .user(userDto)
                .isLiked(isLiked)
                .isReposted(isReposted)
                .repostUsersId(repostUserId)
                .replyPosts(postToDto(post.getReplyPosts(), reqUser))
                .video(post.getVideo())
                .build();
        return postDto;
    }

    public static List<PostDto> postToDto (List<Post> posts, AppUser reqUser){
        List<PostDto> postDtos = new ArrayList<>();
        for (Post post: posts) {
            PostDto postDto = toReplyPostDto(post, reqUser);
            postDtos.add(postDto);
        }
        return postDtos;
    }

    private static PostDto toReplyPostDto(Post post, AppUser reqUser) {
        UserDto userDto = UserDtoMapper.userToDto(post.getAppUser());
        boolean isLiked = PostUtil.isLikedByReqUser(reqUser, post);
        boolean isReposted = PostUtil.isRepostedByReqUser(reqUser, post);
        List<Long> repostUserId = new ArrayList<>();
        for (AppUser user: post.getRepostAppUser()) {
            repostUserId.add(user.getUserId());
        }
        PostDto postDto = PostDto.builder()
                .id(post.getPostId())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .image(post.getImage())
                .totalLikes(post.getAppLikes().size())
                .totalReplies(post.getReplyPosts().size())
                .totalReposts(post.getRepostAppUser().size())
                .user(userDto)
                .isLiked(isLiked)
                .isReposted(isReposted)
                .repostUsersId(repostUserId)
                .replyPosts(postToDto(post.getReplyPosts(), reqUser))
                .video(post.getVideo())
                .build();
        return postDto;
    }
}
