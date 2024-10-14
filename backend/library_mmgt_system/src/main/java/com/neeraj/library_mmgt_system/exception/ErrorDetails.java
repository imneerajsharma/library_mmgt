package com.neeraj.library_mmgt_system.exception;

import java.time.LocalDateTime;

public class ErrorDetails {

    private String message;           // Updated field name for error message
    private String details;
    private LocalDateTime timestamp;

    // Default constructor
    public ErrorDetails() {}

    // Parameterized constructor
    public ErrorDetails(String message, String details, LocalDateTime timestamp) {
        this.message = message;
        this.details = details;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}


