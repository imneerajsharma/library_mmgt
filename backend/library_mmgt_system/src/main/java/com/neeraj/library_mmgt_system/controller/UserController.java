package com.neeraj.library_mmgt_system.controller;

import com.neeraj.library_mmgt_system.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.neeraj.library_mmgt_system.exception.UserException;
import com.neeraj.library_mmgt_system.model.User;
import com.neeraj.library_mmgt_system.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService , BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    // User signup
//    @PostMapping("/signup", consumes = "application/json")
    // @PostMapping(value = "/signup", consumes = "application/json")
    @PostMapping(value = "/signup", consumes = {"application/json", "application/json;charset=UTF-8"})
    public ResponseEntity<User> signup(@RequestBody User user) {
        try {
            // Encrypt the password before saving
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            User savedUser = userService.createMember(user);
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        } catch (UserException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
//    @PostMapping("/signup")
//    public ResponseEntity<ApiResponse> signup(@RequestBody User user) {
//        try {
//            // Validate user fields
//            if (user.getUsername() == null || user.getUsername().isEmpty() ||
//                    user.getEmail() == null || user.getEmail().isEmpty() ||
//                    user.getPassword() == null || user.getPassword().isEmpty()) {
//                return new ResponseEntity<>(new ApiResponse("Username, email, and password are required.", false), HttpStatus.BAD_REQUEST);
//            }
//
//            // Encrypt the password before saving
//            String encodedPassword = passwordEncoder.encode(user.getPassword());
//            user.setPassword(encodedPassword);
//
//            // Attempt to create the user
//            User savedUser = userService.createMember(user);
//            return new ResponseEntity<>(new ApiResponse("User registered successfully.", true), HttpStatus.CREATED);
//        } catch (UserException e) {
//            return new ResponseEntity<>(new ApiResponse("Error: " + e.getMessage(), false), HttpStatus.BAD_REQUEST);
//        }
//    }

    @DeleteMapping("/delete-account")
    public ResponseEntity<ApiResponse> deleteOwnAccount(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        user.setActive(false);  // Soft delete by deactivating the account
        userService.saveUser(user);
        return new ResponseEntity<>(new ApiResponse("Your account has been deactivated.", true), HttpStatus.OK);
    }
    // Update user information
    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestHeader("Authorization") String jwt, @RequestBody User updatedUser) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        user.setUsername(updatedUser.getUsername());
        user.setEmail(updatedUser.getEmail());
        // You may want to allow changing the password as well, but consider security implications
        if (updatedUser.getPassword() != null) {
            String encodedPassword = passwordEncoder.encode(updatedUser.getPassword());
            user.setPassword(encodedPassword);
        }
        User savedUser = userService.saveUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }

    // Get all users (Admin access)
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllMembers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

}

