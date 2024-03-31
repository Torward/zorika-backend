package ru.lomov.zorika_backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lomov.zorika_backend.entities.AppUser;
import ru.lomov.zorika_backend.entities.Post;
import ru.lomov.zorika_backend.exception.PostException;
import ru.lomov.zorika_backend.exception.UserException;
import ru.lomov.zorika_backend.repositories.PostRepository;
import ru.lomov.zorika_backend.request.PostReplyRequest;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    @Override
    public Post createPost(Post req, AppUser user) throws UserException {
        Post post = new Post();
        post.setContent(req.getContent());
        post.setCreatedAt(LocalDateTime.now());
        post.setImage(req.getImage());
        post.setAppUser(user);
        post.setReply(false);
        post.setPost(true);
        post.setVideo(req.getVideo());
        return postRepository.save(post);
    }

    @Override
    public Post repost(Long postId, AppUser user) throws UserException, PostException {
        Post post = findById(postId);
        if (post.getRepostAppUser().contains(user)) {
            post.getRepostAppUser().remove(user);
        } else {
            post.getRepostAppUser().add(user);
        }
        return postRepository.save(post);
    }

    @Override
    public Post findById(Long postId) throws PostException {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostException("Пост не найден!"));
        return post;
    }

    @Override
    public Post removeFromRepost(Long postId, AppUser user) throws PostException, UserException {
        
        return null;
    }

    @Override
    public Post createReply(PostReplyRequest replyRequest, AppUser user) throws PostException {
        Post replyFor = findById(replyRequest.getPostId());
        Post post = new Post();
        post.setContent(replyRequest.getContent());
        post.setCreatedAt(LocalDateTime.now());
        post.setImage(replyRequest.getImage());
        post.setAppUser(user);
        post.setReply(true);
        post.setPost(false);
        post.setReplyFor(replyFor);

        Post savedReply = postRepository.save(post);
//        post.getReplyPosts().add(savedReply);
        replyFor.getReplyPosts().add(savedReply);
        postRepository.save(replyFor);
        return replyFor;
    }

    @Override
    public List<Post> findAllPosts() {
        return postRepository.findAllByIsPostTrueOrderByCreatedAtDesc();
    }

    @Override
    public List<Post> getUserPosts(AppUser user) {
        return postRepository.findByRepostAppUserContainsOrAppUser_UserIdAndIsPostTrueOrderByCreatedAtDesc(user, user.getUserId());
    }

    @Override
    public List<Post> findByLikesContainsUser(AppUser user) {
        return postRepository.findByAppLikesAAndAppUser_UserId(user.getUserId());
    }

    @Override
    public void deletePostById(Long postId, Long userId) throws PostException, UserException {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostException("Пост не найден!"));
        if (!userId.equals(post.getAppUser().getUserId())){
            throw new UserException("Вы не можете удалять пост других пользователей");
        }
        postRepository.deleteById(post.getPostId());
    }
}
