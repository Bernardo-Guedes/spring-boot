package com.example.RadioBrowserAPI.service;

import com.example.RadioBrowserAPI.entities.User;
import com.example.RadioBrowserAPI.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service 
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder  passwordEnconder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEnconder = passwordEncoder;
    }

    public User registerUser(String username, String email, String password){
        if(userRepository.existsByEmail(email)){
            throw new RuntimeException("email");
        }

        if(userRepository.existsByUsername(username)){
            throw new RuntimeException("username");
        }

        String passwordEncoded = passwordEnconder.encode(password);
        User user = new User(username, email, passwordEncoded, "USER");
        return userRepository.save(user);
    }
}
