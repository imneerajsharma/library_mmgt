package com.neeraj.library_mmgt_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.neeraj.library_mmgt_system.model.BookCategory;

public interface CategoryRepository extends JpaRepository<BookCategory, Long> {

    // Method to find a category by its name
    BookCategory findByName(String name);

}

