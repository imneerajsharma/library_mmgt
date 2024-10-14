package com.neeraj.library_mmgt_system.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.neeraj.library_mmgt_system.exception.BorrowException;
import com.neeraj.library_mmgt_system.exception.UserException;
import com.neeraj.library_mmgt_system.model.BorrowRecord;
import com.neeraj.library_mmgt_system.model.User;
import com.neeraj.library_mmgt_system.model.Book;
import com.neeraj.library_mmgt_system.service.BorrowRecordService;
import com.neeraj.library_mmgt_system.service.UserService;
import com.neeraj.library_mmgt_system.service.BookService;

@RestController
@RequestMapping("/api/borrow")
public class BorrowController {

    private final BorrowRecordService borrowRecordService; // Updated
    private final UserService userService;
    private final BookService bookService;

    @Autowired
    public BorrowController(BorrowRecordService borrowRecordService, UserService userService, BookService bookService) {
        this.borrowRecordService = borrowRecordService; // Updated
        this.userService = userService;
        this.bookService = bookService;
    }

    @PostMapping("/{bookId}")
    public ResponseEntity<BorrowRecord> borrowBookHandler(@PathVariable Long bookId,
                                                          @RequestHeader("Authorization") String jwt) throws UserException, BorrowException {
        User user = userService.findUserProfileByJwt(jwt);
        Book book = bookService.findBookById(bookId);
        BorrowRecord borrowRecord = borrowRecordService.borrowBook(user, book); // Updated

        return new ResponseEntity<>(borrowRecord, HttpStatus.OK);
    }

    @PutMapping("/{borrowId}/return")
    public ResponseEntity<BorrowRecord> returnBookHandler(@PathVariable Long borrowId,
                                                          @RequestHeader("Authorization") String jwt) throws UserException, BorrowException {
        User user = userService.findUserProfileByJwt(jwt);
        BorrowRecord borrowRecord = borrowRecordService.returnBook(borrowId); // Updated

        return new ResponseEntity<>(borrowRecord, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<BorrowRecord>> usersBorrowHistoryHandler(@RequestHeader("Authorization") String jwt) throws BorrowException, UserException {
        User user = userService.findUserProfileByJwt(jwt);
        List<BorrowRecord> borrowRecords = borrowRecordService.getUserBorrowingHistory(user.getId()); // Updated

        return new ResponseEntity<>(borrowRecords, HttpStatus.OK);
    }

    @GetMapping("/{borrowId}")
    public ResponseEntity<BorrowRecord> findBorrowRecordHandler(@PathVariable Long borrowId,
                                                                @RequestHeader("Authorization") String jwt) throws BorrowException, UserException {
        User user = userService.findUserProfileByJwt(jwt);
        BorrowRecord borrowRecord = borrowRecordService.findBorrowRecordById(borrowId); // Updated

        return new ResponseEntity<>(borrowRecord, HttpStatus.OK);
    }
}
