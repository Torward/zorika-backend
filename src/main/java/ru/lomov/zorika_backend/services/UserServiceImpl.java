package ru.lomov.zorika_backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lomov.zorika_backend.config.JwtProvider;
import ru.lomov.zorika_backend.entities.AppUser;
import ru.lomov.zorika_backend.exception.UserException;
import ru.lomov.zorika_backend.repositories.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Override
    public AppUser findUserById(Long userId) throws UserException {
        AppUser user = userRepository.findById(userId).orElseThrow(() -> new UserException("Пользователь не найден!"));
        return user;
    }

    @Override
    public AppUser findUserProfileByJwt(String jwt) throws UserException {
        String email = jwtProvider.getEmailFromToken(jwt);
        AppUser user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new UserException("Пользователь не найден!");
        }
        return user;
    }

    @Override
    public AppUser updateUser(Long userId, AppUser reqUser) throws UserException {
        AppUser user = findUserById(userId);
        if (reqUser.getFullName() != null) {
            user.setFullName(reqUser.getFullName());
        }
        if (reqUser.getImage()!= null){
            user.setImage(reqUser.getImage());
        }
        if (reqUser.getBackgroundImage() != null){
            user.setBackgroundImage(reqUser.getBackgroundImage());
        }
        if (reqUser.getLocation() != null){
            user.setLocation(reqUser.getLocation());
        }
        if (reqUser.getBirthDate() != null){
            user.setBirthDate(reqUser.getBirthDate());
        }
        if (reqUser.getBio() != null){
            user.setBio(reqUser.getBio());
        }
        if (reqUser.getWebsite() != null){
            user.setWebsite(reqUser.getWebsite());
        }

        return userRepository.save(user);
    }

    @Override
    public AppUser followUser(Long userId, AppUser user) throws UserException {
        AppUser followToUser = findUserById(userId);
        if (user.getFollowing().contains(followToUser) && followToUser.getFollowers().contains(user)){
            user.getFollowing().remove(followToUser);
            followToUser.getFollowers().remove(user);
        } else {
            user.getFollowing().add(followToUser);
            followToUser.getFollowers().add(user);
        }
        userRepository.save(followToUser);
        userRepository.save(user);
        return followToUser;
    }

    @Override
    public List<AppUser> searchUser(String query) {
        return userRepository.searchUser(query);
    }
}
