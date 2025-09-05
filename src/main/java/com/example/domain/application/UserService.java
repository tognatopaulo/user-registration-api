package com.example.domain.application;

import com.example.domain.entity.User;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class UserService {

    List<User> users = new ArrayList<>();

    public List<User> getUserList() {
        return users;
    }

    public User registerUser(User request) {
        users.add(request);
        return request;
    }
}
