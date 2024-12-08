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

    @Query("""
            SELECT ver FROM VisitorEntryRequest ver
            WHERE ver.user.id=:userId AND (
            ver.visitor.verificationId LIKE %:keyword% OR
            ver.visitor.name LIKE %:keyword% OR
            ver.visitor.company LIKE %:keyword% OR
            ver.department LIKE %:keyword% OR
            ver.nightStay LIKE %:keyword% OR
            ver.permission LIKE %:keyword%
            )
            """)
    Page<VisitorEntryRequest> searchByKeywordAndUserId(String keyword, Long userId, Pageable pageable);

    @Query("""
            SELECT ver FROM VisitorEntryRequest ver
            WHERE ver.permission=:permission AND (
            ver.visitor.verificationId LIKE %:keyword% OR
            ver.visitor.name LIKE %:keyword% OR
            ver.visitor.company LIKE %:keyword% OR
            ver.department LIKE %:keyword% OR
            ver.nightStay LIKE %:keyword% OR
            ver.user.firstName LIKE %:keyword% OR
            ver.user.lastName LIKE %:keyword% OR
            ver.user.designation LIKE %:keyword%
            )
            """)
    Page<VisitorEntryRequest> searchByKeywordAndPermission(String keyword, String permission, Pageable pageable);

    @Query("""
            SELECT ver FROM VisitorEntryRequest ver
            WHERE ver.permission!=:permission AND (
            ver.visitor.verificationId LIKE %:keyword% OR
            ver.visitor.name LIKE %:keyword% OR
            ver.visitor.company LIKE %:keyword% OR
            ver.department LIKE %:keyword% OR
            ver.nightStay LIKE %:keyword% OR
            ver.permission LIKE %:keyword% OR
            ver.user.firstName LIKE %:keyword% OR
            ver.user.lastName LIKE %:keyword% OR
            ver.user.designation LIKE %:keyword%
            )
            """)
    Page<VisitorEntryRequest> searchByKeywordAndPermissionNot(String keyword, String permission, Pageable pageable);

}
