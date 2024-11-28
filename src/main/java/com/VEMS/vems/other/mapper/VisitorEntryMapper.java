package com.VEMS.vems.other.mapper;

import com.VEMS.vems.dto.requestDto.RecordInTimeDto;
import com.VEMS.vems.dto.responseDto.ViewVisitorEntryDto;
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

    public VisitorEntry mapNewVisitorEntry(RecordInTimeDto recordInTimeDto, VisitorEntryRequest visitorEntryRequest) {
        VisitorEntry visitorEntry = new VisitorEntry();
        visitorEntry.setDate(LocalDate.now());
        visitorEntry.setInTime(timeFormatter.currentTime());
        visitorEntry.setOutTime(null);
        visitorEntry.setVehicleNo(recordInTimeDto.getVehicleNo());
        visitorEntry.setPassNo(recordInTimeDto.getPassNo());
        visitorEntry.setVisitorEntryRequest(visitorEntryRequest);
        return visitorEntry;
    }

    public ViewVisitorEntryDto mapViewVisitorEntry(VisitorEntry visitorEntry) {
        ViewVisitorEntryDto viewVisitorEntryDto = new ViewVisitorEntryDto();
        viewVisitorEntryDto.setDate(visitorEntry.getDate());
        viewVisitorEntryDto.setInTime(visitorEntry.getInTime());
        viewVisitorEntryDto.setOutTime(visitorEntry.getOutTime());
        viewVisitorEntryDto.setVehicleNo(visitorEntry.getVehicleNo());
        viewVisitorEntryDto.setPassNo(visitorEntry.getPassNo());
        viewVisitorEntryDto.setVisitorEntryRequest(visitorEntry.getVisitorEntryRequest());
        return viewVisitorEntryDto;
    }
}
