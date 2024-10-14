package com.neeraj.library_mmgt_system.repository;

import com.neeraj.library_mmgt_system.model.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookCategoryRepository extends JpaRepository<BookCategory, Long> {

    // Method to find a category by name
    BookCategory findByName(String name);

//    // Custom query to find a category by name and parent category (if hierarchical structure is needed)
//    @Query("SELECT c FROM BookCategory c WHERE c.name = :name AND c.parentCategory.name = :parentCategoryName")
//    BookCategory findByNameAndParent(@Param("name") String name, @Param("parentCategoryName") String parentCategoryName);
//

}

