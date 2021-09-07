package com.myapp.courier.dto;

public class LoginResponseDTO {

    /*{
        "username": "pss@gmail.com",
            "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1Zjg5M2NmZmRlNTI2ZDAwMTcxNDY4NDciLCJpYXQiOjE2MDI4MzA3NzEsImV4cCI6MTYwMzQzNTU3MX0.bI-vme5wv4_S1cgYS85MRWLNdaK3JaC-wafFR2QWVbo",
            "expiresIn": 168,
            "message": "Welcome! Your logged in"
    }*/

    private String email;
    private String token;
    private int expiresIn;
    private String message;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
