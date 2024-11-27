package com.VEMS.vems.other.mapper;

import com.VEMS.vems.dto.requestDto.AddVisitorEntryDto;
import com.VEMS.vems.entity.VisitorEntry;
import com.VEMS.vems.entity.VisitorEntryRequest;
import com.VEMS.vems.other.timeFormatConfig.TimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class VisitorEntryMapper {

    private final TimeFormatter timeFormatter;

    public VisitorEntry mapNewVisitorEntry(AddVisitorEntryDto addVisitorEntryDto, VisitorEntryRequest visitorEntryRequest) {
        VisitorEntry visitorEntry = new VisitorEntry();
        visitorEntry.setDate(LocalDate.now());
        visitorEntry.setInTime(timeFormatter.currentTime());
        visitorEntry.setOutTime(null);
        visitorEntry.setVehicleNo(addVisitorEntryDto.getVehicleNo());
        visitorEntry.setPassNo(addVisitorEntryDto.getPassNo());
        visitorEntry.setVisitorEntryRequest(visitorEntryRequest);
        return visitorEntry;
    }
}
