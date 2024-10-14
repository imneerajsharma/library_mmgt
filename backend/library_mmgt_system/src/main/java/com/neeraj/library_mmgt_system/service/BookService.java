package com.neeraj.library_mmgt_system.service;

import java.util.List;

import com.neeraj.library_mmgt_system.exception.BookException;
import com.neeraj.library_mmgt_system.model.Book;
import com.neeraj.library_mmgt_system.request.CreateBookRequest;
//import org.hibernate.query.Page;
import org.springframework.data.domain.Page;

public interface BookService {

    // Only for admin
    Book createBook(CreateBookRequest req) throws BookException;

    String deleteBook(Long bookId) throws BookException;

    Book updateBook(Long bookId, Book bookDetails) throws BookException;

    List<Book> getAllBooks();

    // For user and admin both
    Book findBookById(Long id) throws BookException;

    List<Book> findBooksByCategory(String category);

    List<Book> searchBooks(String query);
    Page<Book> getAllBooks(String category, Integer pageNumber, Integer pageSize); // Ensure this matches

    // Pagination and filtering can be implemented here
     //Page<Book> getAllBooks(String category, List<String> colors, List<String> sizes, Integer minPrice, Integer maxPrice, Integer minDiscount, String sort, Integer pageNumber, Integer pageSize);
}

