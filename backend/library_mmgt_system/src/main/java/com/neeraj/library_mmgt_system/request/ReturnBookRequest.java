package com.neeraj.library_mmgt_system.request;

public class ReturnBookRequest {

    private Long bookId;
    private Long memberId;
    private String conditionNotes; // Optional, to record the condition of the book on return

    public ReturnBookRequest() {
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

    public String getConditionNotes() {
        return conditionNotes;
    }

    public void setConditionNotes(String conditionNotes) {
        this.conditionNotes = conditionNotes;
    }
}

