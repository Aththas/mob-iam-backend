package com.VEMS.vems.dto.responseDto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ViewInternEntryDto {
    private LocalDate date;
    private String inTime;
    private String outTime;
    private String vehicleNo;
    private Long passNo;

    private String job;
    private String department;
    private String manager;
    private String intern;
}
