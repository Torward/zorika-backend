package ru.lomov.zorika_backend.services;

import ru.lomov.zorika_backend.entities.AppLike;
import ru.lomov.zorika_backend.entities.AppUser;
import ru.lomov.zorika_backend.exception.PostException;
import ru.lomov.zorika_backend.exception.UserException;

import java.util.List;

public interface LikeService {
    AppLike likePost(Long postId, AppUser user) throws UserException, PostException;
    List<AppLike> getAllLikes(Long postId) throws PostException;
}
