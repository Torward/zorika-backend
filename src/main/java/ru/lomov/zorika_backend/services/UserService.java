package ru.lomov.zorika_backend.services;

import ru.lomov.zorika_backend.entities.AppUser;
import ru.lomov.zorika_backend.exception.UserException;

import java.util.List;

public interface UserService {
    AppUser findUserById(Long userId) throws UserException;
    AppUser findUserProfileByJwt(String jwt) throws UserException;
    AppUser updateUser(Long userId, AppUser user) throws UserException;
    AppUser followUser(Long userId, AppUser user) throws UserException;
    List<AppUser> searchUser(String query);
    void delete(Long userId);
}
