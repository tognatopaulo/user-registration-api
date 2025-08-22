package com.example.adapter.http.api.mapper;

import com.example.adapter.http.api.dto.UserRequest;
import com.example.adapter.http.api.dto.UserResponse;
import com.example.domain.entity.User;

public class UserMapper {


    public static User toEntity(UserRequest request) {
        return new User(
                request.getUserName(),
                request.getEmail(),
                request.getPassword());
    }

    public static UserResponse toResponse(User response) {
        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(response.getUserId());
        userResponse.setUserName(response.getUserName());
        userResponse.setEmail(response.getEmail());
        userResponse.setActive(response.isActive());
        return userResponse;
    }
}
