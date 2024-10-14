package com.neeraj.library_mmgt_system.repository;

import com.neeraj.library_mmgt_system.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    // Optional custom query method to find all addresses by user ID
    List<Address> findByUserId(Long userId);
}

