package com.VEMS.vems.repository;

import com.VEMS.vems.entity.VisitorEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface VisitorEntryRepository extends JpaRepository<VisitorEntry, Long> {

    Optional<VisitorEntry> findByDateAndVisitorEntryRequestId(LocalDate date, Long visitorEntryRequestId);

    Page<VisitorEntry> findAllByDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);
}
