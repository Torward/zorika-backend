package ru.lomov.zorika_backend.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String fullName;
    private String email;
    private String image;
    private String location;
    private String website;
    private String birthDate;
    private String mobile;
    private String backgroundImage;
    private String bio;
    private boolean req_user;
    private boolean followed;
    private boolean isVerified;
    private boolean login_via_google;
    private boolean login_via_vk;
    private List<UserDto>followers = new ArrayList<>();
    private List<UserDto>following = new ArrayList<>();
}
