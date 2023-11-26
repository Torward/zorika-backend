package ru.lomov.zorika_backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lomov.zorika_backend.entities.AppLike;
import ru.lomov.zorika_backend.entities.AppUser;
import ru.lomov.zorika_backend.entities.Post;
import ru.lomov.zorika_backend.exception.PostException;
import ru.lomov.zorika_backend.exception.UserException;
import ru.lomov.zorika_backend.repositories.LikeRepository;
import ru.lomov.zorika_backend.repositories.PostRepository;

import java.util.List;
@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService{
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final PostService postService;

    @Override
    public AppLike likePost(Long postId, AppUser user) throws UserException, PostException {
        AppLike isLikeExist = likeRepository.isLikeExist(user.getUserId(), postId);
        if (isLikeExist!=null){
            likeRepository.deleteById(isLikeExist.getLikeId());
            return isLikeExist;
        }
        Post post = new Post();
        AppLike like = new AppLike();
        like.setPost(post);
        like.setAppUser(user);
        AppLike savedLike = likeRepository.save(like);
        post.getAppLikes().add(savedLike);
        postRepository.save(post);
        return savedLike;
    }

    @Override
    public List<AppLike> getAllLikes(Long postId) throws PostException {
        Post post = postService.findById(postId);
        List<AppLike> likes = likeRepository.findAllByPostId(postId);
        return likes;
    }
}
