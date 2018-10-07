package com.martinb.meli.network;

public class AuthenticationResponse {

    private User user;
    private String description;

    public AuthenticationResponse(User user, String description) {
        this.user = user;
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public String getErrorMessage() {
        return description;
    }

    public boolean isSuccessful() {
        return user != null;
    }
}
