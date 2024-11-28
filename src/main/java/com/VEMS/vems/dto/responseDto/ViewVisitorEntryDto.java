package com.VEMS.vems.dto.responseDto;

import com.VEMS.vems.entity.VisitorEntryRequest;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ViewVisitorEntryDto {
    private LocalDate date;
    private String inTime;
    private String outTime;
    private String vehicleNo;
    private Long passNo;
    private VisitorEntryRequest visitorEntryRequest;
}
