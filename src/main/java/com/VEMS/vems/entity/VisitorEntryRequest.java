package com.VEMS.vems.entity;

import com.VEMS.vems.auth.entity.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
    @JsonBackReference
    private Visitor visitor;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    @JsonBackReference
    private User user;
}
