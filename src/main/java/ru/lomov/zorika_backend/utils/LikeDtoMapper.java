package ru.lomov.zorika_backend.utils;

import ru.lomov.zorika_backend.dto.LikeDto;
import ru.lomov.zorika_backend.dto.PostDto;
import ru.lomov.zorika_backend.dto.UserDto;
import ru.lomov.zorika_backend.entities.AppLike;
import ru.lomov.zorika_backend.entities.AppUser;

import java.util.ArrayList;
import java.util.List;

public class LikeDtoMapper {
    public static LikeDto likeToDto(AppLike like, AppUser reqUser) {
        UserDto user = UserDtoMapper.userToDto(like.getAppUser());
        UserDto reqUserDto = UserDtoMapper.userToDto(reqUser);
        PostDto post = PostDtoMapper.postToDto(like.getPost(), reqUser);
        LikeDto likeDto = new LikeDto();
        likeDto.setId(like.getLikeId());
        likeDto.setPost(post);
        likeDto.setUser(user);

        return likeDto;
    }

    public static List<LikeDto> likeToDtos(List<AppLike> likes, AppUser reqUser) {
        List<LikeDto> likeDtos = new ArrayList<>();
        for (AppLike like : likes) {
            UserDto user = UserDtoMapper.userToDto(like.getAppUser());
            PostDto post = PostDtoMapper.postToDto(like.getPost(), reqUser);
            LikeDto likeDto = new LikeDto();
            likeDto.setId(like.getLikeId());
            likeDto.setPost(post);
            likeDto.setUser(user);
            likeDtos.add(likeDto);
        }
        return likeDtos;
    }
}
