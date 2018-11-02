package com.martinb.meli.network.object_response;

public class AuthenticationResponse {

    private String token;
    private String description;

    public AuthenticationResponse(String token, String description) {
        this.token = token;
        this.description = description;
    }

    public String getToken() {
        return token;
    }

    public String getErrorMessage() {
        return description;
    }

    public boolean isSuccessful() {
        return token != null;
    }
}
