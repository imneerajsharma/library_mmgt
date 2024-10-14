package com.neeraj.library_mmgt_system.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neeraj.library_mmgt_system.exception.BorrowException;
import com.neeraj.library_mmgt_system.model.BorrowRecord;
import com.neeraj.library_mmgt_system.model.Book;
import com.neeraj.library_mmgt_system.model.User;
import com.neeraj.library_mmgt_system.repository.BorrowRecordRepository;
import com.neeraj.library_mmgt_system.model.BorrowStatus;

@Service
public class BorrowRecordServiceImplementation implements BorrowRecordService {

    private final BorrowRecordRepository borrowRecordRepository;

    @Autowired
    public BorrowRecordServiceImplementation(BorrowRecordRepository borrowRecordRepository) {
        this.borrowRecordRepository = borrowRecordRepository;
    }

    @Override
    public BorrowRecord borrowBook(User user, Book book) throws BorrowException {
        // Check if the book is available
        // This method assumes you have a method in the Book model to check availability
        if (!book.getStatus().equals(BorrowStatus.AVAILABLE)) {
            throw new BorrowException("The book is not available for borrowing.");
        }

        // Create a new borrow record
        BorrowRecord borrowRecord = new BorrowRecord();
        borrowRecord.setMember(user);
        borrowRecord.setBook(book);
        borrowRecord.setBorrowDate(LocalDate.now());
        borrowRecord.setStatus(BorrowStatus.BORROWED);

        // Save the borrow record
        return borrowRecordRepository.save(borrowRecord);
    }

    @Override
    public BorrowRecord findBorrowRecordById(Long borrowRecordId) throws BorrowException {
        Optional<BorrowRecord> optionalRecord = borrowRecordRepository.findById(borrowRecordId);
        if (optionalRecord.isPresent()) {
            return optionalRecord.get();
        }
        throw new BorrowException("Borrow record not found with id " + borrowRecordId);
    }

    @Override
    public List<BorrowRecord> getUserBorrowingHistory(Long userId) {
        return borrowRecordRepository.findByMemberId(userId);
    }

    @Override
    public BorrowRecord returnBook(Long borrowRecordId) throws BorrowException {
        BorrowRecord borrowRecord = findBorrowRecordById(borrowRecordId);
        // Assuming you have a method to set the return date and update status
        borrowRecord.setReturnDate(LocalDate.now());
        borrowRecord.setStatus(BorrowStatus.RETURNED);
        return borrowRecordRepository.save(borrowRecord);
    }

    @Override
    public List<BorrowRecord> getAllBorrowRecords() {
        return borrowRecordRepository.findAll();
    }

    @Override
    public void deleteBorrowRecord(Long borrowRecordId) throws BorrowException {
        BorrowRecord borrowRecord = findBorrowRecordById(borrowRecordId);
        borrowRecordRepository.delete(borrowRecord);
    }
}

