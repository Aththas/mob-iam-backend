package com.VEMS.vems.repository;

import com.VEMS.vems.entity.InternEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface InternEntryRepository extends JpaRepository<InternEntry, Long> {

    Optional<InternEntry> findByDateAndIntern(LocalDate date, String intern);

    Page<InternEntry> findAllByDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);
}
