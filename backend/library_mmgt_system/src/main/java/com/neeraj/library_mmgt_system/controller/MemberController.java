package com.neeraj.library_mmgt_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.neeraj.library_mmgt_system.exception.UserException;
import com.neeraj.library_mmgt_system.model.User;
import com.neeraj.library_mmgt_system.users.UserRole;
import com.neeraj.library_mmgt_system.request.CreateMemberRequest;
import com.neeraj.library_mmgt_system.response.ApiResponse;
import com.neeraj.library_mmgt_system.service.UserService;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<User> createMember(@RequestBody CreateMemberRequest request) throws UserException {
        // Set default role as MEMBER
        User newMember = new User();
        newMember.setUsername(request.getUsername());
        newMember.setPassword(request.getPassword());
        newMember.setRole(UserRole.MEMBER);

        User createdMember = userService.createMember(newMember);
        return new ResponseEntity<>(createdMember, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<User>> getAllMembers() {
        List<User> members = userService.getAllMembers();
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    @GetMapping("/active")
    public ResponseEntity<List<User>> getActiveMembers() {
        List<User> activeMembers = userService.findMembersByStatus(true); // Fetch only active members
        return new ResponseEntity<>(activeMembers, HttpStatus.OK);
    }

    @GetMapping("/deleted")
    public ResponseEntity<List<User>> getDeletedMembers() {
        List<User> deletedMembers = userService.findMembersByStatus(false); // Fetch only inactive members
        return new ResponseEntity<>(deletedMembers, HttpStatus.OK);
    }

    @PutMapping("/{memberId}")
    public ResponseEntity<User> updateMember(@PathVariable Long memberId, @RequestBody User memberDetails) throws UserException {
        User updatedMember = userService.updateMember(memberId, memberDetails);
        return new ResponseEntity<>(updatedMember, HttpStatus.OK);
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<ApiResponse> deleteMember(@PathVariable Long memberId) throws UserException {
        userService.deactivateMember(memberId); // Soft delete by setting 'active' to false
        ApiResponse response = new ApiResponse("Member deactivated successfully", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
