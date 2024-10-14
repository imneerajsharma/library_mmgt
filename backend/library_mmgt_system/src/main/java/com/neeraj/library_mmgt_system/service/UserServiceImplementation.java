package com.neeraj.library_mmgt_system.service;

import com.neeraj.library_mmgt_system.exception.UserException;
import com.neeraj.library_mmgt_system.model.User;
import com.neeraj.library_mmgt_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.neeraj.library_mmgt_system.config.JwtTokenProvider;

import java.util.List;

@Service
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImplementation(UserRepository userRepository,
                                     JwtTokenProvider jwtTokenProvider,
                                     BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder; // Inject the password encoder
    }

    @Override
    public User findUserById(Long userId) throws UserException {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserException("User not found with ID: " + userId));
    }

    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {
        String email = jwtTokenProvider.getEmailFromJwtToken(jwt);
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UserException("User does not exist with email: " + email);
        }
        return user;
    }

    @Override
    public User saveUser(User user) throws UserException {
        return userRepository.save(user);
    }

    @Override
    public User createMember(User user) throws UserException {
        // Check if the user already exists
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new UserException("Email already in use: " + user.getEmail());
        }

        // Hash the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public void deactivateMember(Long memberId) throws UserException {
        User member = findUserById(memberId);
        member.setActive(false);
        userRepository.save(member);
    }

    @Override
    public List<User> findMembersByStatus(boolean active) {
        return userRepository.findByActive(active);
    }

    @Override
    public void deleteMember(Long userId) throws UserException {
        User member = findUserById(userId);
        userRepository.delete(member);
    }

    @Override
    public List<User> getAllActiveMembers() {
        return userRepository.findByActive(true);
    }

    @Override
    public User updateMember(Long memberId, User memberDetails) throws UserException {
        User existingMember = findUserById(memberId);
        existingMember.setUsername(memberDetails.getUsername());

        // Optionally, you can allow password updates:
        if (memberDetails.getPassword() != null && !memberDetails.getPassword().isEmpty()) {
            existingMember.setPassword(passwordEncoder.encode(memberDetails.getPassword())); // Hash the new password
        }

        // Update other fields as necessary

        return userRepository.save(existingMember);
    }

    @Override
    public List<User> getAllMembers() {
        return userRepository.findAll(); // Assuming you have a method to get all users
    }
}
