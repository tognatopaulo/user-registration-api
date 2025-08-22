package com.example.adapter.http.api.dto;

public class UserResponse {
    private int userId;
    private String userName;
    private String email;
    private boolean isActive;

    public UserResponse() {
        // Default constructor
    }

    public UserResponse(int userId, String userName, String email, boolean isActive) {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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
