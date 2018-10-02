package com.martinb.meli.network;

public class UserResponse {

    private String email;
    private String password;
    private String token;

    public UserResponse(String email, String password, String token) {
        this.email = email;
        this.password = password;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
