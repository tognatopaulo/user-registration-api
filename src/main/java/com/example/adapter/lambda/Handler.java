package com.example.adapter.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.example.adapter.http.api.dto.UserRequest;
import com.example.adapter.http.api.mapper.UserMapper;
import com.example.domain.application.UserService;
import com.example.domain.entity.User;
import jakarta.inject.Inject;

public class Handler implements RequestHandler<UserRequest, User> {

    @Inject
    UserService userService;

    @Override
    public User handleRequest(UserRequest input, Context context) {
        User user = UserMapper.toEntity(input);
        return userService.registerUser(user);
    }
}
