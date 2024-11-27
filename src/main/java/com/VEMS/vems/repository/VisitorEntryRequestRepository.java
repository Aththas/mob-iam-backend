package com.VEMS.vems.repository;

import com.VEMS.vems.entity.VisitorEntryRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VisitorEntryRequestRepository extends JpaRepository<VisitorEntryRequest, Long> {
    List<VisitorEntryRequest> findAllByVisitorId(Long visitorId);

    Page<VisitorEntryRequest> findAllByUserId(Long userId, Pageable pageable);

    Page<VisitorEntryRequest> findAllByPermission(String permission, Pageable pageable);
    Page<VisitorEntryRequest> findAllByPermissionNot(String permission, Pageable pageable);

}
