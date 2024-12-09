package com.VEMS.vems.repository;

import com.VEMS.vems.entity.VisitorEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface VisitorEntryRepository extends JpaRepository<VisitorEntry, Long> {

    Optional<VisitorEntry> findByDateAndVisitorEntryRequestId(LocalDate date, Long visitorEntryRequestId);

    Page<VisitorEntry> findAllByDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

    @Query("""
        SELECT entry FROM VisitorEntry entry
        WHERE entry.date BETWEEN :fromDate AND :toDate
        AND (
            entry.inTime LIKE %:keyword% OR
            entry.outTime LIKE %:keyword% OR
            entry.vehicleNo LIKE %:keyword% OR
            entry.visitorEntryRequest.department LIKE %:keyword% OR
            entry.visitorEntryRequest.visitor.verificationId LIKE %:keyword% OR
            entry.visitorEntryRequest.visitor.name LIKE %:keyword% OR
            entry.visitorEntryRequest.visitor.company LIKE %:keyword% OR
            entry.visitorEntryRequest.user.firstName LIKE %:keyword% OR
            entry.visitorEntryRequest.user.lastName LIKE %:keyword% OR
            entry.visitorEntryRequest.user.designation LIKE %:keyword%
        )
        """)
    Page<VisitorEntry> searchAllByDateBetweenAndKeyword(String keyword,LocalDate fromDate, LocalDate toDate, Pageable pageable);
}
