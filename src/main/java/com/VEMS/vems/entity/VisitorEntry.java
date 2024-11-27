package com.VEMS.vems.entity;

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
public class VisitorEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private String inTime;
    private String outTime;
    private String vehicleNo;
    private Long passNo;

    @ManyToOne
    @JoinColumn(name = "requestId", nullable = false)
    private VisitorEntryRequest visitorEntryRequest;

}
