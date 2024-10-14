package com.neeraj.library_mmgt_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.neeraj.library_mmgt_system.exception.BookException;
import com.neeraj.library_mmgt_system.model.Book;
import com.neeraj.library_mmgt_system.request.CreateBookRequest;
import com.neeraj.library_mmgt_system.response.ApiResponse;
import com.neeraj.library_mmgt_system.service.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/")
    public ResponseEntity<Book> createBook(@RequestBody CreateBookRequest request) throws BookException {
        Book createdBook = bookService.createBook(request);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }

    @DeleteMapping("/{bookId}/delete")
    public ResponseEntity<ApiResponse> deleteBook(@PathVariable Long bookId) throws BookException {
        String message = bookService.deleteBook(bookId);
        ApiResponse response = new ApiResponse(message, true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Book>> findAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PutMapping("/{bookId}/update")
    public ResponseEntity<Book> updateBook(@RequestBody Book bookDetails, @PathVariable Long bookId) throws BookException {
        Book updatedBook = bookService.updateBook(bookId, bookDetails);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

    @PostMapping("/bulk-create")
    public ResponseEntity<ApiResponse> createMultipleBooks(@RequestBody CreateBookRequest[] requests) throws BookException {
        for (CreateBookRequest request : requests) {
            bookService.createBook(request);
        }
        ApiResponse response = new ApiResponse("Books created successfully", true);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}

