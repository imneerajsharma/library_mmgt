package com.neeraj.library_mmgt_system.repository;


import com.neeraj.library_mmgt_system.model.BorrowRecord;
import com.neeraj.library_mmgt_system.model.BorrowStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {

    // Custom query to get borrow records by user ID and specific statuses
    @Query("SELECT b FROM BorrowRecord b WHERE b.member.id = :memberId AND (b.status = 'AVAILABLE' OR b.status = 'BORROWED')")
    List<BorrowRecord> getMemberBorrowRecords(@Param("memberId") Long memberId);

    // Method to get borrow records by book ID
    List<BorrowRecord> findByBookId(Long bookId);

    // Method to get borrow records by member ID and status
    List<BorrowRecord> findByMemberIdAndStatus(Long memberId, BorrowStatus status);

    List<BorrowRecord> findByMemberId(Long memberId);

}