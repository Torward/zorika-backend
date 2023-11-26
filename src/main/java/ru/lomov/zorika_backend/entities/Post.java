package ru.lomov.zorika_backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long postId;

    @ManyToOne
    private AppUser appUser;

    private String content;
    private String image;
    private String video;
    private boolean isReply;
    private boolean isPost;
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<AppLike> appLikes = new ArrayList<>();

    @OneToMany
    private List<Post> replyPosts = new ArrayList<>();

    @ManyToMany
    private List<AppUser> repostAppUser = new ArrayList<>();

    @ManyToOne
    private Post replyFor;
}
