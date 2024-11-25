package com.VEMS.vems.dto.requestDto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AddVisitorEntryRequestDto {
    private String department;
    private LocalDate startDate;
    private LocalDate endDate;
    private String nightStay;
}
