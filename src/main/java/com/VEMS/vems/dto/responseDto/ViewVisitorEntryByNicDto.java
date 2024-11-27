package com.VEMS.vems.dto.responseDto;

import com.VEMS.vems.entity.VisitorEntryRequest;
import lombok.Data;

@Data
public class ViewVisitorEntryByNicDto {
    private String vehicleNo;
    private String inTime;
    private String outTime;
    private Long passNo;
    private VisitorEntryRequest visitorEntryRequest;
}
