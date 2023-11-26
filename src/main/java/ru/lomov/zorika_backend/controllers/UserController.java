package ru.lomov.zorika_backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lomov.zorika_backend.dto.UserDto;
import ru.lomov.zorika_backend.entities.AppUser;
import ru.lomov.zorika_backend.exception.PostException;
import ru.lomov.zorika_backend.exception.UserException;
import ru.lomov.zorika_backend.services.PostService;
import ru.lomov.zorika_backend.services.UserService;
import ru.lomov.zorika_backend.utils.UserDtoMapper;
import ru.lomov.zorika_backend.utils.UserUtil;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final PostService postService;

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getUserProfile(@RequestHeader("Authorization") String jwt) throws UserException{
        AppUser user = userService.findUserProfileByJwt(jwt);
        UserDto userDto = UserDtoMapper.userToDto(user);
        userDto.setReq_user(true);
        return new ResponseEntity<>(userDto, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId, @RequestHeader("Authorization") String jwt) throws UserException{
        AppUser reqUser = userService.findUserProfileByJwt(jwt);
        AppUser user = userService.findUserById(userId);
        UserDto userDto = UserDtoMapper.userToDto(user);
        userDto.setReq_user(UserUtil.isReqUser(reqUser, user));
        userDto.setFollowed(UserUtil.isFollowedByReqUser(reqUser, user));
        return new ResponseEntity<>(userDto, HttpStatus.ACCEPTED);
    }
@GetMapping("/search")
    public ResponseEntity<List<UserDto>> searchUser(@RequestParam String query, @RequestHeader("Authorization") String jwt) throws UserException{
        AppUser reqUser = userService.findUserProfileByJwt(jwt);
        List<AppUser> users = userService.searchUser(query);
        List<UserDto> userDtos = UserDtoMapper.userToDto(users);
        return new ResponseEntity<>(userDtos, HttpStatus.ACCEPTED);
    }
@PutMapping
    public ResponseEntity<UserDto> updateUser(@RequestBody AppUser req, @RequestHeader("Authorization") String jwt) throws UserException{
        AppUser reqUser = userService.findUserProfileByJwt(jwt);
        AppUser user = userService.updateUser(reqUser.getUserId(), req);
        UserDto userDto = UserDtoMapper.userToDto(user);
        return new ResponseEntity<>(userDto, HttpStatus.ACCEPTED);
    }
@PutMapping("/{userId}/follow")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long userId, @RequestHeader("Authorization") String jwt) throws UserException{
        AppUser reqUser = userService.findUserProfileByJwt(jwt);
        AppUser user = userService.followUser(userId, reqUser);
        UserDto userDto = UserDtoMapper.userToDto(user);
        userDto.setFollowed(UserUtil.isFollowedByReqUser(reqUser, user));
        return new ResponseEntity<>(userDto, HttpStatus.ACCEPTED);
    }

}
