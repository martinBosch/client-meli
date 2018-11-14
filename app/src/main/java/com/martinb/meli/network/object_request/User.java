package com.martinb.meli.network.object_request;

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

    public User() {}

    public String getEmail() {
        return email;
    }

    public String getDisplayName() {
        return display_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
