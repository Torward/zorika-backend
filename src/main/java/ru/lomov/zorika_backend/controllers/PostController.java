package ru.lomov.zorika_backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lomov.zorika_backend.dto.PostDto;
import ru.lomov.zorika_backend.entities.AppUser;
import ru.lomov.zorika_backend.entities.Post;
import ru.lomov.zorika_backend.exception.PostException;
import ru.lomov.zorika_backend.exception.UserException;
import ru.lomov.zorika_backend.request.PostReplyRequest;
import ru.lomov.zorika_backend.response.ApiResponse;
import ru.lomov.zorika_backend.services.PostService;
import ru.lomov.zorika_backend.services.UserService;
import ru.lomov.zorika_backend.utils.PostDtoMapper;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody Post req, @RequestHeader("Authorization") String jwt) throws PostException, UserException {
        AppUser user = userService.findUserProfileByJwt(jwt);
        Post post = postService.createPost(req, user);
        PostDto postDto = PostDtoMapper.postToDto(post, user);
        return new ResponseEntity<>(postDto, HttpStatus.CREATED);
    }

    @PostMapping("/reply")
    public ResponseEntity<PostDto> createReply(@RequestBody PostReplyRequest req, @RequestHeader("Authorization") String jwt) throws PostException, UserException {
        AppUser user = userService.findUserProfileByJwt(jwt);
        Post post = postService.createReply(req, user);
        PostDto postDto = PostDtoMapper.postToDto(post, user);
        return new ResponseEntity<>(postDto, HttpStatus.CREATED);
    }

    @PutMapping("/{postId}/repost")
    public ResponseEntity<PostDto> repost(@PathVariable Long postId, @RequestHeader("Authorization") String jwt) throws PostException, UserException {
        AppUser user = userService.findUserProfileByJwt(jwt);
        Post post = postService.repost(postId, user);
        PostDto postDto = PostDtoMapper.postToDto(post, user);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> findPostByPostId(@PathVariable Long postId, @RequestHeader("Authorization") String jwt) throws PostException, UserException {
        AppUser user = userService.findUserProfileByJwt(jwt);
        Post post = postService.findById(postId);
        PostDto postDto = PostDtoMapper.postToDto(post, user);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<PostDto>> findAllPosts(@RequestHeader("Authorization") String jwt) throws PostException, UserException {
        AppUser user = userService.findUserProfileByJwt(jwt);
        List<Post> posts = postService.findAllPosts();
        List<PostDto> postDtos = PostDtoMapper.postToDto(posts, user);
        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostDto>> findAllUsersPost(@PathVariable Long userId, @RequestHeader("Authorization") String jwt) throws PostException, UserException{
        AppUser user = userService.findUserProfileByJwt(jwt);
        AppUser reqUser = userService.findUserById(userId);
        List<Post> posts = postService.getUserPosts(reqUser);
        List<PostDto> postDtos = PostDtoMapper.postToDto(posts, user);
        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }
    @GetMapping("/user/{userId}/likes")
    public ResponseEntity<List<PostDto>> findPostByLikesContainsUser(@PathVariable Long userId, @RequestHeader("Authorization") String jwt) throws PostException, UserException{
        AppUser user = userService.findUserProfileByJwt(jwt);
//        AppUser reqUser = userService.findUserById(userId);
        List<Post> posts = postService.findByLikesContainsUser(user);
        List<PostDto> postDtos = PostDtoMapper.postToDto(posts, user);
        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long postId, @RequestHeader("Authorization") String jwt) throws PostException, UserException {
        AppUser user = userService.findUserProfileByJwt(jwt);
        postService.deletePostById(postId, user.getUserId());
        ApiResponse response = new ApiResponse("Пост удалён!", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
