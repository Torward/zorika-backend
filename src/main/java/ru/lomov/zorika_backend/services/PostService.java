package ru.lomov.zorika_backend.services;

import ru.lomov.zorika_backend.entities.AppUser;
import ru.lomov.zorika_backend.entities.Post;
import ru.lomov.zorika_backend.exception.PostException;
import ru.lomov.zorika_backend.exception.UserException;
import ru.lomov.zorika_backend.request.PostReplyRequest;

import java.util.List;

public interface PostService {

    Post createPost(Post req, AppUser user) throws UserException;
    Post repost(Long postId, AppUser user) throws UserException, PostException;
    Post findById(Long postId) throws PostException;
    Post removeFromRepost(Long postId, AppUser user) throws PostException, UserException;
    Post createReply(PostReplyRequest replyRequest, AppUser user) throws PostException;
    List<Post> findAllPosts();
    List<Post> getUserPosts(AppUser user);
    List<Post> findByLikesContainsUser(AppUser user);
    void deletePostById(Long postId, Long userId) throws PostException, UserException;
}
