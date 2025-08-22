package com.example.adapter.persistence;

import com.example.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    void saveUser(User user);
    Optional<User> findUserById(String userId);
    void deleteUser(String userId);
    List<User> findAllUsers();
}
