package com.example.domain.application;

import com.example.adapter.persistence.UserRepository;
import com.example.domain.entity.User;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUserList() {
        return userRepository.findAllUsers();
    }

    public User registerUser(User request) {
        userRepository.saveUser(request);
        return request;
    }
}
