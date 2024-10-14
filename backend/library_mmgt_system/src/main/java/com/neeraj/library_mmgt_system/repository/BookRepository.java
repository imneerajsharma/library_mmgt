package com.neeraj.library_mmgt_system.repository;

import com.neeraj.library_mmgt_system.model.Book;
import com.neeraj.library_mmgt_system.model.BookStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    // Find books by category name (case insensitive)
    @Query("SELECT b FROM Book b WHERE LOWER(b.category.name) = :category")
    List<Book> findByCategory(@Param("category") String category);

    // Search books by title, author, or description (case insensitive)
    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE %:query% OR LOWER(b.author) LIKE %:query% OR LOWER(b.isbn) LIKE %:query%")
    List<Book> searchBooks(@Param("query") String query);

    // Filter books by category, price range, discount, and sort by price (if applicable)
    @Query("SELECT b FROM Book b " +
            "WHERE (b.category.name = :category OR :category = '') " +
            "AND (:minPrice IS NULL OR :maxPrice IS NULL OR b.price BETWEEN :minPrice AND :maxPrice) " +
            "AND (:status IS NULL OR b.status = :status) " +
            "ORDER BY " +
            "CASE WHEN :sort = 'price_low' THEN b.price END ASC, " +
            "CASE WHEN :sort = 'price_high' THEN b.price END DESC")
    List<Book> filterBooks(
            @Param("category") String category,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("status") BookStatus status,
            @Param("sort") String sort
    );
}

