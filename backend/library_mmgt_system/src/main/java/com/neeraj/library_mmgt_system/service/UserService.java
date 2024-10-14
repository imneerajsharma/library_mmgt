package com.neeraj.library_mmgt_system.service;

import com.neeraj.library_mmgt_system.exception.UserException;
import com.neeraj.library_mmgt_system.model.User;

import java.util.List;

public interface UserService {

    // Retrieve a user by their ID
    User findUserById(Long userId) throws UserException;

    // Find user profile using JWT for authentication
    User findUserProfileByJwt(String jwt) throws UserException;

    // Create a new member
    User createMember(User user) throws UserException;

    // Deactivate a member
    void deactivateMember(Long memberId) throws UserException;

    // Find members by active status
    List<User> findMembersByStatus(boolean active);

    // Delete a member
    void deleteMember(Long userId) throws UserException;

    // Retrieve all active members
    List<User> getAllActiveMembers();

    // Retrieve all members
    List<User> getAllMembers();

    // Update a member
    User updateMember(Long memberId, User memberDetails) throws UserException;

    // Save an existing user
    User saveUser(User user) throws UserException;
}
