package com.VEMS.vems.entity;

import com.VEMS.vems.auth.entity.user.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VisitorEntryRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String department;
    private LocalDate startDate;
    private LocalDate endDate;
    private String nightStay;
    private String permission;

    @ManyToOne
    @JoinColumn(name = "visitorId", nullable = false)
    private Visitor visitor;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;
}
