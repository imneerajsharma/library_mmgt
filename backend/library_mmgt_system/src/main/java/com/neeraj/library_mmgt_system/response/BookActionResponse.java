package com.neeraj.library_mmgt_system.response;

public class BookActionResponse {

    private Long bookId;
    private String message;
    private boolean success;

    public BookActionResponse() {
        // Default constructor
    }

    public BookActionResponse(Long bookId, String message, boolean success) {
        this.bookId = bookId;
        this.message = message;
        this.success = success;
    }

    // Getters and Setters

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
