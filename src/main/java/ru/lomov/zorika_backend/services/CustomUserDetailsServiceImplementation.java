package ru.lomov.zorika_backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.lomov.zorika_backend.entities.AppUser;
import ru.lomov.zorika_backend.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsServiceImplementation implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findUserByEmail(username);
        if (user == null || user.isLogin_with_google() || user.isLogin_with_vk()){
            throw new UsernameNotFoundException("Имя пользователя: "+username+" не найдено!");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        return new User(user.getEmail(), user.getPassword(), authorities);
    }
}
