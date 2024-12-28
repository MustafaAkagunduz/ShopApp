package com.noisy.rrssProject.model.entity;

import jakarta.validation.constraints.Email;

public class LoginRequest {

    @Email
    private final String email;
    private final String password;

    private LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static LoginRequestBuilder builder() {
        return new LoginRequestBuilder();
    }

    public static class LoginRequestBuilder {

        private String email;
        private String password;

        public LoginRequestBuilder email(String email) {
            this.email = email;
            return this;
        }

        public LoginRequestBuilder password(String password) {
            this.password = password;
            return this;
        }

        public LoginRequest build() {
            return new LoginRequest(email, password);
        }
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
