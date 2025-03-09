package com.example.classup.classup.classup.mycollege.ui.auth;

public class Users {
    private String email;
    private String name;
    private String password;
    private String uid;

    public Users(String email, String name, String password, String uid) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}

