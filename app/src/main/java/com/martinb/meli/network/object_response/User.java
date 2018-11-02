package com.martinb.meli.network.object_response;

public class User {

    private String email;
    private String display_name;
    private String phone;
    private String password;

    public User(String email, String password, String displayName, String phone) {
        this.email = email;
        this.display_name = displayName;
        this.phone = phone;
        this.password = password;
    }
}
