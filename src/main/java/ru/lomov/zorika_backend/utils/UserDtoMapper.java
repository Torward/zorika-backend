package ru.lomov.zorika_backend.utils;

import ru.lomov.zorika_backend.dto.UserDto;
import ru.lomov.zorika_backend.entities.AppUser;

import java.util.ArrayList;
import java.util.List;

public class UserDtoMapper {
    public static UserDto userToDto(AppUser user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getUserId());
        userDto.setEmail(user.getEmail());
        userDto.setFullName(user.getFullName());
        userDto.setImage(user.getImage());
        userDto.setBackgroundImage(user.getBackgroundImage());
        userDto.setBio(user.getBio());
        userDto.setBirthDate(user.getBirthDate());
        userDto.setFollowers(userToDto(user.getFollowers()));
        userDto.setFollowing(userToDto(user.getFollowing()));
        userDto.setLogin_via_google(user.isLogin_with_google());
        userDto.setLogin_via_vk(user.isLogin_with_vk());
        userDto.setLocation(user.getLocation());
//        userDto.setVerified(false);
        return userDto;
    }

    public static List<UserDto> userToDto(List<AppUser> followers) {
        List<UserDto> userDtos = new ArrayList<>();
        for (AppUser user: followers){
            UserDto userDto = new UserDto();
            userDto.setId(user.getUserId());
            userDto.setEmail(user.getEmail());
            userDto.setFullName(user.getFullName());
            userDto.setImage(user.getImage());
            userDtos.add(userDto);
        }
        return userDtos;
    }
}
