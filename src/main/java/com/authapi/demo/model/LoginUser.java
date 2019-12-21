package com.authapi.demo.model;

import javax.validation.constraints.NotBlank;

public class LoginUser {

    @NotBlank(message = "userame is mandatory")
    private String username;

    @NotBlank(message = "password is mandatory")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
