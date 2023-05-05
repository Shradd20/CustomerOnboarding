package com.example.loginapplication;

import java.io.Serializable;

public class SignInResponse  {

    Users user;
    String jwt;

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
