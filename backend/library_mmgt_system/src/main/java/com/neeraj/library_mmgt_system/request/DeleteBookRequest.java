package com.neeraj.library_mmgt_system.request;

public class DeleteBookRequest {

    private Long bookId;
    private String isbn;

    public DeleteBookRequest() {
        // Default constructor
    }

    // Getters and Setters

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}

