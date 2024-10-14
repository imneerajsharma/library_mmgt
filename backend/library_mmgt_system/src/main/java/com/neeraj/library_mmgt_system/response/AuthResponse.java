package com.neeraj.library_mmgt_system.response;

public class AuthResponse {
    private String jwt;
    private boolean status;

    public AuthResponse() {
        // Default constructor
    }

    public AuthResponse(String jwt, boolean status) {
        this.jwt = jwt;
        this.status = status;
    }

    // Getters and Setters
    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
