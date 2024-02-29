package ru.lomov.zorika_backend.controllers;

import jdk.jshell.spi.ExecutionControl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.lomov.zorika_backend.config.JwtProvider;
import ru.lomov.zorika_backend.entities.AppUser;
import ru.lomov.zorika_backend.entities.Verification;
import ru.lomov.zorika_backend.exception.UserException;
import ru.lomov.zorika_backend.repositories.UserRepository;
import ru.lomov.zorika_backend.response.AuthResponse;
import ru.lomov.zorika_backend.services.CustomUserDetailsServiceImplementation;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserDetailsServiceImplementation customUserDetailsServiceImplementation;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody AppUser user) throws UserException {
            String email = user.getEmail();
            String password = user.getPassword();
            String fullName = user.getFullName();
            String birthDate = user.getBirthDate();
            AppUser isEmailExist = userRepository.findUserByEmail(email);
            if (isEmailExist != null){
                throw new UserException("Этот адрес электронной почты уже используется!");
            }
            AppUser createdUser = new AppUser();
            createdUser.setEmail(email);
            createdUser.setFullName(fullName);
            createdUser.setPassword(passwordEncoder.encode(password));
            createdUser.setBirthDate(birthDate);
            createdUser.setVerification(new Verification());
           userRepository.save(createdUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        AuthResponse response = new AuthResponse(token,true);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signIn(@RequestBody AppUser user){
        String username = user.getEmail();
        String password = user.getPassword();
        Authentication authentication = authenticate(username, password);
        String token = jwtProvider.generateToken(authentication);
        AuthResponse response = new AuthResponse(token, true);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserDetailsServiceImplementation.loadUserByUsername(username);
        if (userDetails == null){
            throw new BadCredentialsException("Неверное имя пользователя!");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Неверные имя пользователя или пароль...");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
