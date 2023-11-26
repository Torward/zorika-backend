package ru.lomov.zorika_backend.utils;

import ru.lomov.zorika_backend.entities.AppUser;

public class UserUtil {
    public static boolean isReqUser(AppUser reqUser, AppUser user){
        return reqUser.getUserId().equals(user.getUserId());
    }
    public static boolean isFollowedByReqUser(AppUser reqUser, AppUser user){
        return reqUser.getFollowing().contains(user);
    }
}
