package ru.lomov.zorika_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.lomov.zorika_backend.entities.AppUser;
import ru.lomov.zorika_backend.entities.Post;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByIsPostTrueOrderByCreatedAtDesc();
    List<Post> findByRepostAppUserContainsOrAppUser_UserIdAndIsPostTrueOrderByCreatedAtDesc(AppUser appUser, Long userId);
    List<Post> findByAppLikesContainingOrderByCreatedAtDesc(AppUser appUser);

    @Query("select p from Post p join p.appLikes l where l.appUser.userId=:userId")
    List<Post> findByAppLikesAAndAppUser_UserId(Long userId);
}
