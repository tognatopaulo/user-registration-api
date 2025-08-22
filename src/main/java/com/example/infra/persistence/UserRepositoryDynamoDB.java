package com.example.infra.persistence;

import com.example.adapter.persistence.UserRepository;
import com.example.domain.entity.User;

import java.util.List;
import java.util.Optional;

public class UserRepositoryDynamoDB implements UserRepository {
    @Override
    public void saveUser(User user) {

    }

    @Override
    public Optional<User> findUserById(String userId) {
        return Optional.empty();
    }

    @Override
    public void deleteUser(String userId) {

    }

    @Override
    public List<User> findAllUsers() {
        return List.of();
    }
}
