package com.example.adapter.http.api.dto;

public class UserResponse {
    private String userId;
    private String userName;
    private String email;
    private boolean isActive;

    public UserResponse() {
        // Default constructor
    }

    public UserResponse(String userId, String userName, String email, boolean isActive) {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
