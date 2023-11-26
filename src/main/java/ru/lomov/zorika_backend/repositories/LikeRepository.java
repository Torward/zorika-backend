package ru.lomov.zorika_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.lomov.zorika_backend.entities.AppLike;

import java.util.List;
@Repository
public interface LikeRepository extends JpaRepository<AppLike, Long> {
    @Query("select l from AppLike l where l.appUser.userId=:userId and  l.post.postId=:postId")
    AppLike isLikeExist(@Param("userId") Long userId, @Param("postId") Long postId);

    @Query("select l from AppLike l where l.post.postId=:postId")
    List<AppLike> findAllByPostId(@Param("postId") Long postId);
}
