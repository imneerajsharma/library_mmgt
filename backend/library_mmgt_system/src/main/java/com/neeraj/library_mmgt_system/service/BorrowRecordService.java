package com.neeraj.library_mmgt_system.service;

import java.util.List;

import com.neeraj.library_mmgt_system.exception.BorrowException;
import com.neeraj.library_mmgt_system.model.BorrowRecord;
import com.neeraj.library_mmgt_system.model.Book;
import com.neeraj.library_mmgt_system.model.User;

public interface BorrowRecordService {

    // Method to handle borrowing a book
    BorrowRecord borrowBook(User user, Book book) throws BorrowException;

    // Retrieve a borrow record by its ID
    BorrowRecord findBorrowRecordById(Long borrowRecordId) throws BorrowException;

    // Get the borrowing history of a user
    List<BorrowRecord> getUserBorrowingHistory(Long userId);

    // Mark the book as returned
    BorrowRecord returnBook(Long borrowRecordId) throws BorrowException;

    // Get all borrow records
    List<BorrowRecord> getAllBorrowRecords();

    // Delete a borrow record
    void deleteBorrowRecord(Long borrowRecordId) throws BorrowException;
}

