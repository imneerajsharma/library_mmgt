package com.neeraj.library_mmgt_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.neeraj.library_mmgt_system.config.JwtTokenProvider;
import com.neeraj.library_mmgt_system.exception.UserException;
import com.neeraj.library_mmgt_system.model.User;
import com.neeraj.library_mmgt_system.users.UserRole;
import com.neeraj.library_mmgt_system.repository.UserRepository;
import com.neeraj.library_mmgt_system.request.LoginRequest;
import com.neeraj.library_mmgt_system.response.AuthResponse;
import com.neeraj.library_mmgt_system.service.CustomUserDetails;
import com.neeraj.library_mmgt_system.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private CustomUserDetails customUserDetails;

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> registerUser(@Valid @RequestBody User user) throws UserException {

        String username = user.getUsername();
        String password = user.getPassword();

        // Check if a user with the same username already exists
        if (userRepository.findByUsername(username) != null) {
            throw new UserException("Username is already in use by another account");
        }

        // Set default role as MEMBER, or it can be passed in the request for flexibility
        user.setRole(UserRole.MEMBER);
        user.setPassword(passwordEncoder.encode(password));
        User savedUser = userRepository.save(user);

        // Authenticate the newly created user
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate a JWT token
        String token = jwtTokenProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse(token, true);

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        // Authenticate user
        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate a JWT token
        String token = jwtTokenProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse(token, true);

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @PostMapping("/signup-librarian")
    public ResponseEntity<AuthResponse> createLibrarian(@Valid @RequestBody User librarianUser,
                                                        @RequestHeader("Authorization") String jwt) throws UserException {

        // Verify that the request is coming from an existing librarian
        User existingUser = userService.findUserProfileByJwt(jwt);
        if (existingUser.getRole() != UserRole.LIBRARIAN) {
            throw new UserException("Only librarians can add new librarians.");
        }

        String username = librarianUser.getUsername();
        String password = librarianUser.getPassword();

        // Check if a user with the same username already exists
        if (userRepository.findByUsername(username) != null) {
            throw new UserException("Username is already in use by another account");
        }

        // Set role as LIBRARIAN
        librarianUser.setRole(UserRole.LIBRARIAN);
        librarianUser.setPassword(passwordEncoder.encode(password));
        User savedLibrarian = userRepository.save(librarianUser);

        // Authenticate the newly created librarian
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate a JWT token for the new librarian
        String token = jwtTokenProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse(token, true);

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserDetails.loadUserByUsername(username);

        if (userDetails == null || !passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
