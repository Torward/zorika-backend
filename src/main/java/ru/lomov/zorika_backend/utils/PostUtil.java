package ru.lomov.zorika_backend.utils;

import ru.lomov.zorika_backend.entities.AppLike;
import ru.lomov.zorika_backend.entities.AppUser;
import ru.lomov.zorika_backend.entities.Post;

public class PostUtil {
    public static boolean isLikedByReqUser(AppUser reqUser, Post post) {
        for (AppLike like: post.getAppLikes()) {
            if (like.getAppUser().getUserId().equals(reqUser.getUserId())){
                return true;
            }
        }
        return false;
    }
    public static boolean isRepostedByReqUser(AppUser reqUser, Post post){
        for (AppUser user: post.getRepostAppUser()) {
            if (user.getUserId().equals(reqUser.getUserId())){
                return true;
            }
        }
        return false;
    }
}
