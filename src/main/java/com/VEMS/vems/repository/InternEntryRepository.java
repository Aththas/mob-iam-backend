package com.VEMS.vems.repository;

import com.VEMS.vems.entity.InternEntry;
import com.VEMS.vems.entity.VisitorEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface InternEntryRepository extends JpaRepository<InternEntry, Long> {

    Optional<InternEntry> findByDateAndIntern(LocalDate date, String intern);

    Page<InternEntry> findAllByDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

    @Query("""
        SELECT entry FROM InternEntry entry
        WHERE entry.date BETWEEN :fromDate AND :toDate
        AND (
            entry.inTime LIKE %:keyword% OR
            entry.outTime LIKE %:keyword% OR
            entry.vehicleNo LIKE %:keyword% OR
            entry.intern LIKE %:keyword%
        )
        """)
    Page<InternEntry> searchAllByDateBetweenAndKeyword(String keyword, LocalDate fromDate, LocalDate toDate, Pageable pageable);
}
