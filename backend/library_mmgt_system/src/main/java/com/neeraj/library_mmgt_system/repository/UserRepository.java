package com.neeraj.library_mmgt_system.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.neeraj.library_mmgt_system.model.User;
import com.neeraj.library_mmgt_system.users.UserRole;

public interface UserRepository extends JpaRepository<User, Long> {

    // Retrieve users by their active status
    List<User> findByActive(boolean active);

    // Optional: Find a user by username (useful for authentication)
    User findByUsername(String username);

    // Optional: Find users by role (e.g., MEMBER or LIBRARIAN)
    List<User> findByRole(UserRole role);

    // Optional: Find a user by email (if applicable)
    User findByEmail(String email);
}
