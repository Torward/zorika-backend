package ru.lomov.zorika_backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppLike {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long likeId;

    @ManyToOne
    private AppUser appUser;

    @ManyToOne
    private Post post;
}
