package com.neeraj.library_mmgt_system.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.neeraj.library_mmgt_system.exception.BookException;
import com.neeraj.library_mmgt_system.model.Book;
//import com.neeraj.library_mmgt_system.model.Category;
import com.neeraj.library_mmgt_system.model.BookCategory;
import com.neeraj.library_mmgt_system.model.BookStatus;
import com.neeraj.library_mmgt_system.repository.BookRepository;
import com.neeraj.library_mmgt_system.repository.CategoryRepository;
import com.neeraj.library_mmgt_system.request.CreateBookRequest;

@Service
public class BookServiceImplementation implements BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public BookServiceImplementation(BookRepository bookRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Book createBook(CreateBookRequest req) throws BookException {
        // Check and create category if it doesn't exist
        BookCategory category = categoryRepository.findByName(req.getCategoryName());
        if (category == null) {
            category = new BookCategory();
            category.setName(req.getCategoryName());
            //category.setLevel(1);
            categoryRepository.save(category);
        }

        // Create a new book
        Book book = new Book();
        book.setTitle(req.getTitle());
        book.setAuthor(req.getAuthor());
        book.setISBN(req.getISBN());
        book.setPrice(req.getPrice());
        book.setStatus(BookStatus.AVAILABLE); // Set initial status
        book.setCreatedAt(LocalDateTime.now());
        book.setCategory(category);

        // Save and return the created book
        return bookRepository.save(book);
    }

    @Override
    public String deleteBook(Long bookId) throws BookException {
        Book book = findBookById(bookId);
        bookRepository.delete(book);
        return "Book deleted successfully";
    }

    @Override
    public Book updateBook(Long bookId, Book bookDetails) throws BookException {
        Book book = findBookById(bookId);
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setISBN(bookDetails.getISBN());
        book.setStatus(bookDetails.getStatus());
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book findBookById(Long id) throws BookException {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            return optionalBook.get();
        }
        throw new BookException("Book not found with id " + id);
    }

    @Override
    public List<Book> findBooksByCategory(String categoryName) {
        return bookRepository.findByCategory(categoryName);
    }

    @Override
    public List<Book> searchBooks(String query) {
        return bookRepository.searchBooks(query);
    }

    @Override
    public Page<Book> getAllBooks(String category, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<Book> books = bookRepository.findByCategory(category);
        int startIndex = (int) pageable.getOffset();
        int endIndex = Math.min(startIndex + pageable.getPageSize(), books.size());
        List<Book> pageContent = books.subList(startIndex, endIndex);
        return new PageImpl<>(pageContent, pageable, books.size());
    }
//    @Override
//    public Page<Book> getAllBooks(String category, Integer pageNumber, Integer pageSize) {
//        Pageable pageable = PageRequest.of(pageNumber, pageSize);
//        List<Book> books = bookRepository.findByCategory(category);
//        int startIndex = (int) pageable.getOffset();
//        int endIndex = Math.min(startIndex + pageable.getPageSize(), books.size());
//        List<Book> pageContent = books.subList(startIndex, endIndex);
//        return new PageImpl<>(pageContent, pageable, books.size());
//    }
}



