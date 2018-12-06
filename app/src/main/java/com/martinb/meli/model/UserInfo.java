package com.martinb.meli.model;

import java.io.Serializable;

public class UserInfo implements Serializable {

    private String display_name;
    private String email;
    private String phone;

    public UserInfo(String displayName, String email, String phone) {
        this.display_name = displayName;
        this.email = email;
        this.phone = phone;
    }

    public String getDisplayName() {
        return display_name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}
