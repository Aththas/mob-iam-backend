package com.VEMS.vems.dto.requestDto;

import lombok.Data;

@Data
public class AddVisitorEntryDto {
    private String vehicleNo;
    private Long passNo;
    private Long visitorEntryRequestId;
}
