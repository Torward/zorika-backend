package ru.lomov.zorika_backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lomov.zorika_backend.dto.LikeDto;
import ru.lomov.zorika_backend.entities.AppLike;
import ru.lomov.zorika_backend.entities.AppUser;
import ru.lomov.zorika_backend.exception.PostException;
import ru.lomov.zorika_backend.exception.UserException;
import ru.lomov.zorika_backend.services.LikeService;
import ru.lomov.zorika_backend.services.UserService;
import ru.lomov.zorika_backend.utils.LikeDtoMapper;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LikeController {
    private final LikeService likeService;
    private final UserService userService;

    @PostMapping("/{postId}/likes")
    public ResponseEntity<LikeDto> likePost(@PathVariable Long postId, @RequestHeader("Authorization") String jwt) throws PostException, UserException {
        AppUser user = userService.findUserProfileByJwt(jwt);
        AppLike like = likeService.likePost(postId, user);
        LikeDto likeDto = LikeDtoMapper.likeToDto(like, user);
        return new ResponseEntity<>(likeDto, HttpStatus.CREATED);
    }

    @PostMapping("/post/{postId}")
    public ResponseEntity<List<LikeDto>> getAllLikes(@PathVariable Long postId, @RequestHeader("Authorization") String jwt) throws PostException, UserException {
        AppUser user = userService.findUserProfileByJwt(jwt);
        List<AppLike> likes = likeService.getAllLikes(postId);
        List<LikeDto> likeDtos = LikeDtoMapper.likeToDtos(likes, user);
        return new ResponseEntity<>(likeDtos, HttpStatus.CREATED);
    }

}
