package com.VEMS.vems.repository;

import com.VEMS.vems.entity.VisitorEntryRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitorEntryRequestRepository extends JpaRepository<VisitorEntryRequest, Long> {
    @Query("""
            SELECT v FROM VisitorEntryRequest v WHERE v.visitor.id=:visitorId
            """)
    List<VisitorEntryRequest> findAllByVisitorId(Long visitorId);
}
