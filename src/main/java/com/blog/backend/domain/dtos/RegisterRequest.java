package com.blog.backend.domain.dtos;

public class RegisterRequest {
    private String email;
    private String password;
    private String name;
    private String confirmPassword;


    public RegisterRequest(String email, String password, String name, String confirmPassword) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.confirmPassword = confirmPassword;
    }

    public RegisterRequest() {
    }


    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public RegisterRequest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "RegisterRequest{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }
}
