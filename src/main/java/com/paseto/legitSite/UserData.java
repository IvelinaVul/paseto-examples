package com.paseto.legitSite;

import java.util.UUID;

public class UserData {

    private String id;
    private String password;
    private String data;

    public UserData(String password, String data) {
        this.id = UUID.randomUUID().toString();
        this.password = password;
        this.data = data;
    }

    public boolean isValidPassword(String password) {
        return this.password.equals(password);
    }

    public String getId() {
        return id;
    }

    public String getData() {
        return data;
    }
}
