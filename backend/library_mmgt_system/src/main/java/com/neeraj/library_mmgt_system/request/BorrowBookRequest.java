package com.neeraj.library_mmgt_system.request;

public class BorrowBookRequest {

    private Long bookId;
    private Long memberId;

    public BorrowBookRequest() {
        // Default constructor
    }

    // Getters and Setters

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
}

