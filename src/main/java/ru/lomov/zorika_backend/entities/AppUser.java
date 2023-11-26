package ru.lomov.zorika_backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "app_user")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    private String fullName;
    private String location;
    private String website;
    private String birthDate;
    private String email;
    private String password;
    private String mobile;
    private String image;
    private String backgroundImage;
    private String bio;
    private boolean requested_usr;
    private boolean login_with_google;
    private boolean login_with_vk;

    @JsonIgnore
    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL)
    private List<AppLike> appLikes = new ArrayList<>();

    @JsonIgnore
    @ManyToMany
    private List<AppUser> followers = new ArrayList<>();

    @JsonIgnore
    @ManyToMany
    private List<AppUser> following = new ArrayList<>();

    @Embedded
    private Verification verification;

}
