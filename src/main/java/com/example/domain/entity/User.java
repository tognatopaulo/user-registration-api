package com.example.domain.entity;

import io.quarkus.elytron.security.common.BcryptUtil;

public class User {
    private String userId;
    private String userName;
    private String email;
    private String password;
    private boolean isActive = false;
    private String activationCode;

    public User(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        setPassword(password);
        this.activationCode = generateActivationCode();
    }

    private String generateActivationCode() {
        int code = new java.security.SecureRandom().nextInt(1_000_000);
        return String.format("%06d", code);
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

    public void setPassword(String password) {
        this.password = BcryptUtil.bcryptHash(password);
    }

    public String getPassword() {
        return password;
    }

    public boolean checkPassword(String password) {
        return BcryptUtil.matches(password, this.password);
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }
}
